import java.util.*;
import java.time.*;

public class StarProtectVehicleSystem {

    static class UnderWriter {
        int id;
        String name;
        String dob;
        String joiningDate;
        String password;

        UnderWriter(int id, String name, String dob, String joiningDate, String password) {
            this.id = id;
            this.name = name;
            this.dob = dob;
            this.joiningDate = joiningDate;
            this.password = password;
        }

        public String toString() {
            return "UnderWriterId: " + id + ", Name: " + name + ", DOB: " + dob + ", JoiningDate: " + joiningDate + ", Password: " + password;
        }
    }

    static class VehicleInsurance {
        int policyNo;
        String vehicleNo;
        String vehicleType;
        String customerName;
        int engineNo;
        int chasisNo;
        String phoneNo;
        String type; 
        double premiumAmt;
        LocalDate fromDate;
        LocalDate toDate;
        int underWriterId;

        VehicleInsurance(int policyNo, String vehicleNo, String vehicleType, String customerName, int engineNo,
                         int chasisNo, String phoneNo, String type, double premiumAmt,
                         LocalDate fromDate, LocalDate toDate, int underWriterId) {
            this.policyNo = policyNo;
            this.vehicleNo = vehicleNo;
            this.vehicleType = vehicleType;
            this.customerName = customerName;
            this.engineNo = engineNo;
            this.chasisNo = chasisNo;
            this.phoneNo = phoneNo;
            this.type = type;
            this.premiumAmt = premiumAmt;
            this.fromDate = fromDate;
            this.toDate = toDate;
            this.underWriterId = underWriterId;
        }

        public String toString() {
            return "PolicyNo: " + policyNo + ", VehicleNo: " + vehicleNo + ", VehicleType: " + vehicleType +
                    ", Customer: " + customerName + ", EngineNo: " + engineNo + ", ChasisNo: " + chasisNo +
                    ", Phone: " + phoneNo + ", Type: " + type + ", Premium: " + premiumAmt +
                    ", From: " + fromDate + ", To: " + toDate + ", UWId: " + underWriterId;
        }
    }

    static Scanner sc = new Scanner(System.in);
    static ArrayList<UnderWriter> underwriters = new ArrayList<>();
    static ArrayList<VehicleInsurance> vehicles = new ArrayList<>();

    static int uwCounter = 1;
    static int policyCounter = 100;

    //  for Admin 
    static void adminMenu() {
        while (true) {
            System.out.println("\n******** ADMIN MENU ********");
            System.out.println("1. Register UnderWriter");
            System.out.println("2. Search UnderWriter by Id");
            System.out.println("3. Update UnderWriter Password");
            System.out.println("4. Delete UnderWriter");
            System.out.println("5. View All UnderWriters");
            System.out.println("6. View All Vehicles by UnderWriter Id");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: registerUnderWriter(); break;
                case 2: searchUnderWriterById(); break;
                case 3: updateUnderWriterPassword(); break;
                case 4: deleteUnderWriter(); break;
                case 5: viewAllUnderWriters(); break;
                case 6: viewVehiclesByUnderWriter(); break;
                case 7: return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    static void registerUnderWriter() {
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter DOB (yyyy-mm-dd): ");
        String dob = sc.nextLine();
        System.out.print("Enter Joining Date (yyyy-mm-dd): ");
        String jd = sc.nextLine();
        String password = "uw@" + uwCounter + "123"; // default password rule

        UnderWriter uw = new UnderWriter(uwCounter++, name, dob, jd, password);
        underwriters.add(uw);
        System.out.println("UnderWriter registered successfully. Default password: " + password);
    }

    static void searchUnderWriterById() {
        System.out.print("Enter UW Id: ");
        int id = sc.nextInt();
        sc.nextLine();
        for (UnderWriter uw : underwriters) {
            if (uw.id == id) {
                System.out.println(uw);
                return;
            }
        }
        System.out.println("Invalid UnderWriter Id");
    }

    static void updateUnderWriterPassword() {
        System.out.print("Enter UW Id: ");
        int id = sc.nextInt();
        sc.nextLine();
        for (UnderWriter uw : underwriters) {
            if (uw.id == id) {
                System.out.print("Enter new password: ");
                uw.password = sc.nextLine();
                System.out.println("Password updated.");
                return;
            }
        }
        System.out.println("Invalid UnderWriter Id");
    }

    static void deleteUnderWriter() {
        System.out.print("Enter UW Id: ");
        int id = sc.nextInt();
        sc.nextLine();
        Iterator<UnderWriter> it = underwriters.iterator();
        while (it.hasNext()) {
            UnderWriter uw = it.next();
            if (uw.id == id) {
                System.out.println("Press 1 to confirm delete, 2 to cancel");
                int c = sc.nextInt();
                if (c == 1) {
                    it.remove();
                    System.out.println("UnderWriter deleted.");
                }
                return;
            }
        }
        System.out.println("Invalid UnderWriter Id");
    }

    static void viewAllUnderWriters() {
        if (underwriters.isEmpty()) System.out.println("No UnderWriters found.");
        else underwriters.forEach(System.out::println);
    }

    static void viewVehiclesByUnderWriter() {
        System.out.print("Enter UW Id: ");
        int id = sc.nextInt();
        sc.nextLine();
        boolean found = false;
        for (VehicleInsurance v : vehicles) {
            if (v.underWriterId == id) {
                System.out.println(v);
                found = true;
            }
        }
        if (!found) System.out.println("Invalid UnderWriter Id or no vehicles found.");
    }

    // for UnderWriter 
    static void underWriterMenu(UnderWriter uw) {
        while (true) {
            System.out.println("\n****** UNDERWRITER MENU *******");
            System.out.println("1. Create Vehicle Insurance");
            System.out.println("2. Renew Policy");
            System.out.println("3. Change Policy Type");
            System.out.println("4. View Policies");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: createVehicleInsurance(uw.id); break;
                case 2: renewPolicy(uw.id); break;
                case 3: changePolicyType(uw.id); break;
                case 4: viewPolicies(uw.id); break;
                case 5: return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    static void createVehicleInsurance(int uwId) {
        System.out.print("Enter Vehicle No: ");
        String vno = sc.nextLine();
        System.out.print("Enter Vehicle Type: ");
        String vtype = sc.nextLine();
        System.out.print("Enter Customer Name: ");
        String cname = sc.nextLine();
        System.out.print("Enter Engine No: ");
        int eno = sc.nextInt();
        System.out.print("Enter Chasis No: ");
        int cno = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Phone No: ");
        String phone = sc.nextLine();
        System.out.print("Enter Policy Type (Full/Third Party): ");
        String type = sc.nextLine();

        double premium = type.equalsIgnoreCase("Full") ? 5000 : 3000;
        LocalDate from = LocalDate.now();
        LocalDate to = from.plusDays(365);

        VehicleInsurance v = new VehicleInsurance(policyCounter++, vno, vtype, cname, eno, cno, phone,
                type, premium, from, to, uwId);
        vehicles.add(v);
        System.out.println("Vehicle Insurance Created: " + v);
    }

    static void renewPolicy(int uwId) {
        System.out.print("Enter Policy Id: ");
        int pid = sc.nextInt();
        sc.nextLine();
        for (VehicleInsurance v : vehicles) {
            if (v.policyNo == pid && v.underWriterId == uwId) {
                System.out.println("Policy Found: " + v);
                System.out.print("Press R to renew: ");
                String c = sc.nextLine();
                if (c.equalsIgnoreCase("R")) {
                    System.out.print("Enter new premium: ");
                    double p = sc.nextDouble();
                    sc.nextLine();
                    LocalDate from = LocalDate.now();
                    LocalDate to = from.plusDays(365);
                    VehicleInsurance nv = new VehicleInsurance(policyCounter++, v.vehicleNo, v.vehicleType, v.customerName,
                            v.engineNo, v.chasisNo, v.phoneNo, v.type, p, from, to, uwId);
                    vehicles.add(nv);
                    System.out.println("Policy Renewed: " + nv);
                }
                return;
            }
        }
        System.out.println("Invalid Policy Id");
    }

    static void changePolicyType(int uwId) {
        System.out.print("Enter Policy Id: ");
        int pid = sc.nextInt();
        sc.nextLine();
        for (VehicleInsurance v : vehicles) {
            if (v.policyNo == pid && v.underWriterId == uwId) {
                if (v.type.equalsIgnoreCase("Third Party")) {
                    System.out.println("Cannot upgrade Third Party to Full.");
                } else {
                    System.out.println("Press U to downgrade to Third Party: ");
                    String c = sc.nextLine();
                    if (c.equalsIgnoreCase("U")) {
                        v.type = "Third Party";
                        v.premiumAmt = 3000;
                        System.out.println("Updated: " + v);
                    }
                }
                return;
            }
        }
        System.out.println("Invalid Policy Id");
    }

    static void viewPolicies(int uwId) {
        System.out.println("1. View All\n2. By Vehicle No\n3. By Policy Id");
        int c = sc.nextInt();
        sc.nextLine();
        switch (c) {
            case 1:
                vehicles.stream().filter(v -> v.underWriterId == uwId).forEach(System.out::println);
                break;
            case 2:
                System.out.print("Enter Vehicle No: ");
                String vno = sc.nextLine();
                vehicles.stream().filter(v -> v.vehicleNo.equals(vno) && v.underWriterId == uwId).forEach(System.out::println);
                break;
            case 3:
                System.out.print("Enter Policy Id: ");
                int pid = sc.nextInt();
                vehicles.stream().filter(v -> v.policyNo == pid && v.underWriterId == uwId).forEach(System.out::println);
                break;
            default: System.out.println("Invalid choice");
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n********* STAR PROTECT VEHICLE SYSTEM **********");
            System.out.println("1. Admin Login");
            System.out.println("2. UnderWriter Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                System.out.print("Enter Admin Username: ");
                String u = sc.nextLine();
                System.out.print("Enter Password: ");
                String p = sc.nextLine();
                if (u.equals("admin") && p.equals("admin123")) {
                    adminMenu();
                } else {
                    System.out.println("Invalid Admin Credentials!");
                }
            } else if (choice == 2) {
                System.out.print("Enter UW Id: ");
                int id = sc.nextInt();
                sc.nextLine();
                System.out.print("Enter Password: ");
                String pass = sc.nextLine();
                for (UnderWriter uw : underwriters) {
                    if (uw.id == id && uw.password.equals(pass)) {
                        underWriterMenu(uw);
                    }
                }
                System.out.println("Invalid UnderWriter Credentials!");
            } else if (choice == 3) {
                System.out.println("Exiting System. Goodbye!");
                break;
            } else {
                System.out.println("Invalid Choice!");
            }
        }
    }
}
