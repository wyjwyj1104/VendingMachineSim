/** Simulation of the vending machine.
 *
 * Source: https://mail.google.com/mail/u/0/?ui=2&view=btop&ver=ops2cvpehp6#attid%253Datt_178a83b38e1cabe5_0.1_1610b7cc_161343ef_e8f71fe1_c11c7668_b6a6679d%25252F2021_Q2_java_exercise_vending_machine.docx
 * Start Date: 202104080000
 * End Date: 202104080312
 *
 * @author Woo Yong Jang
 * @version 1.0
 * @since 1.0
*/

import java.util.*;
import java.io.*;

import java.util.Scanner;

class Slot {
  /**
   * This class manipulates the data stored in the vending machine to
   * manage item detials of the vending machine slots.
   *
   */
  private String slotItemName;
  private int slotItemQuantity;
  private float slotItemPrice;

  public Slot(String slotItemName, int slotItemQuantity, float slotItemPrice) {
    this.slotItemName = slotItemName;
    this.slotItemQuantity = slotItemQuantity;
    this.slotItemPrice = slotItemPrice;
  }
  public String toString() {
    return "[ " + slotItemName + "," + slotItemQuantity + "," + slotItemPrice + " ]";
  }
  public String getSlotItemName() {
    return this.slotItemName;
  }
  public int getSlotItemQuantity() {
    return this.slotItemQuantity;
  }
  public void setSlotItemQuantity(int slotItemQuantity) {
    this.slotItemQuantity = slotItemQuantity;
  }
  public void addSlotItemQuantity(int slotItemQuantity) {
    this.slotItemQuantity += slotItemQuantity;
  }
  public float getSlotItemPrice() {
    return this.slotItemPrice;
  }
}

class VendingMachine {
  /**
   * This class simulates the vending machine operations and manipulating the
   * data stored in the vending machine slots and items.
   *
   */

  // Assuming that overall items is 100 and limited to 10 slots, which means each slot can hold 10 items limited.
  public static final int MAX_ITEM_PER_SLOT = 10;
  public static final int MAX_SLOTS = 10;

  private HashMap<Integer, Slot> slots;

  private int numSlots;
  private int numItems;
  public VendingMachine() {
    this.slots = new HashMap<>();
    this.numSlots = 0;
    this.numItems = 0;
  }

  public HashMap<Integer, Slot> getSlots() {
    return this.slots;
  }

  public Slot getSlotItem(int slotNumber) {
    return this.slots.get(slotNumber);
  }

  public Boolean inRange(int slotNumber) {
    return this.slots.containsKey(slotNumber);
  }

  public Boolean insertSlot(String slotItemName, int slotItemQuantity, float slotItemPrice) {
    /**
     * Inserts vending machine items read from file.
     *
     * <p>
     * This method uses main insert logic to find empty slots in the
     * vending machine to fill in the slot items.
     * Finds the next empty slot and fill, if no item is found in the vending
     * machine.
     * <p>
     * Finds the next empty slot and fill the rest to limit 10 items per slot,
     * if same item is found in the vending machine, and left over goes to the
     * next available slot.
     *
     * @return Boolean value indicating the insert process success/failure.
     */
    Boolean found = false;
    Slot foundSlot = null;
    int currentQuantity = 0;
    // Check duplicates.
    for (Map.Entry<Integer, Slot> s : this.slots.entrySet()) {
      Slot slot = s.getValue();
      int slotNumber = s.getKey();
      if (slot.getSlotItemName().equals(slotItemName)) {
        found = true;
        foundSlot = slot;
        currentQuantity = foundSlot.getSlotItemQuantity() + slotItemQuantity;
      }
    }

    // If slot is not filled, fill in maximum 10 items in the slot.
    if (!found) {
      foundSlot = new Slot(slotItemName, slotItemQuantity, slotItemPrice);
      currentQuantity = slotItemQuantity;
    }
    // If slot is full, add the rest of the items to the next slot.
    if (currentQuantity >= MAX_ITEM_PER_SLOT) {
      foundSlot.setSlotItemQuantity(MAX_ITEM_PER_SLOT);
      if (!found) {
        this.numSlots++;
        if (this.numSlots > MAX_SLOTS)
          return false;
        this.slots.put(this.numSlots, foundSlot);
      }
      currentQuantity -= MAX_ITEM_PER_SLOT;
    }
    // Fill in the next available limited 10 items per slot.
    while (currentQuantity > 0) {
      this.numSlots++;
      if (this.numSlots > MAX_SLOTS)
        return false;
      if (currentQuantity / MAX_ITEM_PER_SLOT > 0)
        this.slots.put(this.numSlots, new Slot(slotItemName, MAX_ITEM_PER_SLOT, slotItemPrice));
      else
        this.slots.put(this.numSlots, new Slot(slotItemName, currentQuantity, slotItemPrice));
      currentQuantity -= MAX_ITEM_PER_SLOT;
    }
    return true;
  }

  public String toString() {
    /**
     * Overriding toString method for printing.
     *
     * <p>
     * This method visualizes the hash map into viewable data format of each
     * slots in the vending machine.
     *
     * @return Returns the visulization of the vending machine as string.
     */
    return Arrays.asList(this.slots).toString();
  }
}


public class VendingMachineSim {
    public static final String TEST_FILE_PATH = "test_input.txt";

    public static void loadVMFromFile(VendingMachine vm) {
      /**
       * Load initial data from local file to fill in the vending machine.
       *
       * <p>
       * This method loads the initial file data and initializes the vending
       * machine and insert the slot items into the machine.
       *
       * @param vm VendingMachine object to initialize the initial data into.
       * @throw Exception if vending machine is invalid.
       * @throw Exception if failed to load file.
       * @throw Exception if file is empty.
       * @throw Exception if file has invalid format.
       * @throw NumberFormatException if file parsing failed.
       */
      String path = System.getProperty("user.dir") + '/' + TEST_FILE_PATH;
      try {
        if (vm == null)
          throw new Exception("Invalid vending machine");
        File file = new File(path);
        if (file.length() == 0)
          throw new Exception("File is empty");
        if (file.exists()) {
          Scanner sc = new Scanner(file);
          while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] item = line.replaceAll("\\s+","").split("\\|");
            if (item.length != 3)
              throw new Exception("Invalid file format");

            String slotItemName = item[0];
            int slotItemQuantity = 0;
            Float slotItemPrice = 0.0f;
            try {
              slotItemQuantity = Integer.parseInt(item[1]);
              slotItemPrice = Float.parseFloat(item[2]);
            }
            catch (NumberFormatException e) {
              System.out.println(e.getClass());
            }

            if (slotItemQuantity != 0) {
              if (vm.insertSlot(slotItemName, slotItemQuantity, slotItemPrice))
                System.out.println("Inserted [ " + slotItemName + "," + slotItemQuantity + "," + slotItemPrice + " ]");

            }
          }
        }
      }
      catch (Exception e) {
        System.out.println(e.getClass());
      }
    }

    public static Integer tryParseInt(String text) {
      try {
        return Integer.parseInt(text);
      } catch (NumberFormatException e) {
        return -1;
      }
    }
    public static Float tryParseFloat(String text) {
      try {
        return Float.parseFloat(text);
      } catch (NumberFormatException e) {
        return -1.0f;
      }
    }

    public static void mainLoop(VendingMachine vm) {
      /**
       * Main logic function of the program
       *
       * <p>
       * This method runs the main loop of the program, simulating the vending
       * machine flow, getting user input and provide output of the vending
       * machine simulation process.
       *
       * <p>
       * Let user input various command to simulate the vending machine,
       * re-request user for valid input on the vending process, also handling
       * the business and calculation logic simulating the real-life vending
       * machine
       *
       * <ul>
       * Main flow:
       *    <li> Shows the vending machine data.
       *    <li> Ask user to select the slot number.
       *    <li> Ask user to select the quantity of the items to buy.
       *    <li> Insert money to match the total amount.
       *    <li> Vends the items and return the remaing money to the user.
       * </ul>
       *
       * <p>
       * Quit command:
       *    quit - Terminates the vending machine and returns the remainder of the money.
       *
       * @param vm Main VendingMachine object of the simulation.
       */
      float userInput = 0.00f;
      Boolean userStarted = false;
      Boolean userSlotNumber = false;
      Slot selectedItem = null;
      int selectedItemQuantity = 0;
      Boolean userItemQuantity = false;
      Float change = 0.00f;
      try {
        while (true) {
          if (!userStarted) {
            System.out.println("\n\n==================================================");
            System.out.println("Welcome! Please select slot number!");
            System.out.println(vm.toString());
            System.out.println("==================================================");
            userStarted = true;
          }
          String input = reader.readLine();
          if (input.equals("quit")) {
            System.out.println("Quitting, returning remainder of $" + Math.round((userInput)*100)/100 + "!");
            break;
          }
          if (input.length() != 0) {
            //==================================================================
            // Selecting slot number
            //==================================================================
            if (!userSlotNumber) {
              if (vm.inRange(tryParseInt(input))) {
                userSlotNumber = true;
                selectedItem = vm.getSlotItem(tryParseInt(input));
                System.out.println("You have selected slot number " + input + ":" + selectedItem.getSlotItemName() + "!");
              }
              else {
                System.out.println("Pleae select correct slot number!");

              }
            }

            if (userSlotNumber && selectedItem != null) {
              //================================================================
              // Selecting quantity
              //================================================================
              if (!userItemQuantity) {
                int leftOverQuantity = selectedItem.getSlotItemQuantity();
                System.out.println("Please select item quantity (" + leftOverQuantity + ")!"); // Assuming vending machine is loaded properly
                input = reader.readLine();
                selectedItemQuantity = tryParseInt(input);
                while (selectedItemQuantity <= 0 || selectedItemQuantity > leftOverQuantity) {
                  System.out.println("Please select correct item quantity (" + leftOverQuantity + ")!");
                  input = reader.readLine();
                  selectedItemQuantity = tryParseInt(input);
                }
                System.out.println("You have selected quantity " + input + "!");
                userItemQuantity = true;
              }

              //================================================================
              // Inserting money
              //================================================================
              if (userItemQuantity) {
                float totalPrice = (selectedItemQuantity * selectedItem.getSlotItemPrice());
                if (userInput <= 0.00f) {
                  System.out.println("Total price is $" + totalPrice + ", Please insert change $0.05, $0.10, $0.25, $1.00, $5.00");
                  input = reader.readLine();
                  change = tryParseFloat(input);
                }
                while (change != 0.05f && change != 0.10f && change != 0.25f && change != 1.00f && change != 5.00f) {
                  System.out.println("Invalid input! Please use $0.05, $0.10, $0.25, $1.00, $5.00");
                  input = reader.readLine();
                  change = tryParseFloat(input);
                }
                userInput += change;
                System.out.println("You have inserted $" + userInput + "!");
                if (userInput >= totalPrice) {
                  selectedItem.setSlotItemQuantity(selectedItem.getSlotItemQuantity() - selectedItemQuantity);
                  System.out.println("Vending " + selectedItemQuantity + " " + selectedItem.getSlotItemName() + ", returning remainder of $" + Math.round((userInput-totalPrice)*100)/100 + "!");
                  userStarted = false;
                  userInput = 0.00f;
                  userSlotNumber = false;
                  selectedItem = null;
                  selectedItemQuantity = 0;
                  userItemQuantity = false;
                  change = 0.00f;
                }
              }
            }
          }
        }
      }
      catch (Exception e) {
        System.out.println("Invalid input!");
      }
    }

    public static void main(String[] args) {
      /**
       * Main function of the program
       *
       * This method initializes the data and runs the main loop of the program.
       */
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      VendingMachine vm = new VendingMachine();
      loadVMFromFile(vm);
      mainLoop(vm);
    }
}
