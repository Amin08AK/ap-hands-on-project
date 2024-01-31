/*
 *
 *Java program to manage people in it
 *First of all input manager's information
 *Then Guests can sing up and select from menu
 *
 */

import java.util.Scanner;

public class Main {
    public static final int arrSize = 99999999;
    public static int guestCounter = 0;
    public static int personnelCounter = 0;
    public static int roomCounter = 0;

    public static Guest[] guest = new Guest[arrSize];
    public static Personnel[] personnel = new Personnel[arrSize];
    public static Room[] room = new Room[arrSize];


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println(ConsoleColor.BLUE + "Welcome\n");
        System.out.println("Please Enter System Manager's Information");

        Manager manager ;
        manager = inputSingUpInfoForManager();
        while (true) {
            logInMenu();
            int mainMenuChoose = sc.nextInt();
            outer:
            switch (mainMenuChoose) {
                case 0:
                    return;
                case 1: //log in guest
                    if (guestCounter == 0) System.out.println(ConsoleColor.RED + "There is no registered guest!");
                    else {
                        int guestWhoLogin = guestMenu(); // for handling room cancel reservation!

                            if ( guestWhoLogin == -1) {
                                break;
                            }
                            else {
                                int guestMenuChoose = sc.nextInt();
                                switch (guestMenuChoose) {
                                    case 0:
                                        break outer;
                                    case 1: //reserve room
                                        if (roomCounter == 0) {
                                            System.out.println("Sorry, There is no available room right now!");
                                            break outer;
                                        }
                                        reserveRoom(guestWhoLogin);
                                        break;
                                    case 2: // cancel reservation
                                        System.out.println("Enter -1 to Back to main menu");
                                        System.out.println("Enter Room Number: ");
                                        int roomNumber = sc.nextInt();
                                        if (roomNumber == -1) break outer;
                                        while (roomNumber >= roomCounter) {
                                            System.out.print(ConsoleColor.RED + "Invalid room number!\nEnter Room number again: ");
                                            roomNumber = sc.nextInt();
                                            if (roomNumber == -1) break outer;
                                        }
                                        if (room[roomNumber].isEmpty()){
                                            System.out.println(ConsoleColor.RED+"This room is not reserved!"+ConsoleColor.CYAN_BRIGHT);
                                            break outer;
                                        }
                                        if(room[roomNumber].getGuestReservedThisRoomIndex() == guestWhoLogin){
                                            System.out.println("Are you sure you want to cancel your reservation?(y/n): ");
                                            String checkAns = sc.next();
                                            while (!checkAns.equalsIgnoreCase("Y") && !checkAns.equalsIgnoreCase("n")) {
                                                System.out.println(ConsoleColor.RED + "Please Enter a valid character!" + ConsoleColor.CYAN_BRIGHT);
                                                checkAns = sc.next();
                                            }
                                            if (checkAns.equalsIgnoreCase("n")) {
                                                System.out.println("OK :)");
                                            } else {
                                                room[roomNumber].setEmpty(true);
                                                System.out.println("Room "+roomNumber+" is now available");
                                            }

                                        }else {
                                            System.out.println(ConsoleColor.RED+"This room is not reserved by you!"+ConsoleColor.CYAN_BRIGHT);
                                        }
                                        break outer;
                                    case 3: // edit guest info
                                        Guest tempGuest;
                                        tempGuest = inputSingUpInfoForGuest();
                                        System.out.println("Do you want to replace these info?(y/n)");
                                        String checkChar = sc.next();
                                        while (!checkChar.equalsIgnoreCase("Y") && !checkChar.equalsIgnoreCase("n")) {
                                            System.out.println(ConsoleColor.RED + "Please Enter a valid character!" + ConsoleColor.BLUE);
                                            checkChar = sc.next();
                                        }
                                        if (checkChar.equalsIgnoreCase("n")) {
                                            System.out.println("OK :)");
                                        } else {
                                            guest[guestWhoLogin] = tempGuest;
                                        }
                                        break outer;
                                    case 4: //printGuestInfo
                                        guest[guestWhoLogin].personInfoPrint();
                                        System.out.println("Press Enter to continue...");
                                        sc.nextLine();
                                        sc.nextLine();
                                        break outer;
                                }
                            }
                    }
                    break;
                case 2://log in personnel
                    if (personnelCounter == 0)
                        System.out.println(ConsoleColor.RED + "There is no registered personnel");
                    else {
                        int personnelWhoLogIn = personnelLogin();
                        if (personnelWhoLogIn == -1)break ;
                        else {
                            int personnelMenu = sc.nextInt();
                            switch (personnelMenu){
                                case 1:
                                    personnel[personnelWhoLogIn].personInfoPrint();
                                    System.out.println("Press Enter to continue...");
                                    sc.nextLine();
                                    sc.nextLine();
                                    break outer;
                                case 2:
                                    break outer;
                                default:
                                    System.out.println(ConsoleColor.RED+"invalid input"+ConsoleColor.WHITE_BRIGHT);
                                    break outer;
                            }
                        }

                    }
                    break;
                case 3:// log in manager
                    int y = managerMenu(manager);
                    if (y == -1) break;
                    int managerMenuChoose = sc.nextInt();
                        switch (managerMenuChoose) {
                            case 0://back
                                break outer;
                            case 1://register Personnel
                                personnel[personnelCounter] = inputSingUpInfoForPersonnel();
                                for (int i = 0; i < personnelCounter; i++) {
                                    while (personnel[personnelCounter].getID().equals(personnel[i].getID())) {
                                        System.out.print(ConsoleColor.RED + "ERROR!\nThis Id is taken! Tyr another one: " + ConsoleColor.YELLOW_BRIGHT);
                                        personnel[personnelCounter].setID(sc.next());
                                    }
                                }
                                System.out.print(ConsoleColor.GREEN_BRIGHT + "What is \"" + personnel[personnelCounter].getName() + "\" with id:  \"" + personnel[personnelCounter].getID() + "\" Job? ");
                                personnel[personnelCounter].setJob(sc.next());
                                System.out.print("What is \"" + personnel[personnelCounter].getName() + "\" with id:  \"" + personnel[personnelCounter].getID() + "\"   Responsibilities? ");
                                String temp1 = sc.next();
                                String temp2 = sc.nextLine();
                                String responsibilities = temp1 + temp2;
                                personnel[personnelCounter].setResponsibilities(responsibilities);
                                System.out.println("Job: " + personnel[personnelCounter].getJob());
                                System.out.print("Press Enter to continue...");
                                sc.nextLine();
                                personnelCounter++;
                                break outer;
                            case 2://fire personnel
                                String firedPersonnelID;

                                if (personnelCounter == 0)
                                    System.out.println(ConsoleColor.RED + "There is no registered personnel");
                                else {
                                    System.out.println("Enter personnel's id that you want to fire! :");
                                    firedPersonnelID = sc.next();
                                    for (int i = 0; i < personnelCounter; i++) {
                                        if (personnel[i].getID().equals(firedPersonnelID)) {
                                            personnel[i].setIsBaned(true);
                                            System.out.println("This person is now Banned!");
                                            break outer;
                                        }
                                    }
                                    System.out.println(ConsoleColor.RED+"ID not found!!");
                                }
                                break outer;
                            case 3: //add room
                                room[roomCounter] = addRoom();
                                System.out.println("Done :)");
                                break outer;
                            case 4://edit room
                                if (roomCounter == 0)
                                    System.out.println(ConsoleColor.RED + "There is no Room" + ConsoleColor.YELLOW_BRIGHT);

                                System.out.println("Enter -1 to Back to main menu");
                                System.out.println("Enter Room Number: ");
                                int roomNumberInput = sc.nextInt();
                                if (roomNumberInput == -1) break outer;
                                while (roomNumberInput >= roomCounter || !room[roomNumberInput].isAvailable()) {
                                    System.out.print(ConsoleColor.RED + "Invalid room number!\nEnter Room number again: ");
                                    roomNumberInput = sc.nextInt();
                                    if (roomNumberInput == -1) break outer;
                                }
                                if (!room[roomNumberInput].isAvailable()) {
                                    System.out.println(ConsoleColor.RED + "This Room is out off service!");
                                    break outer;
                                }
                                System.out.println(ConsoleColor.YELLOW_BRIGHT + "Room Type: ");
                                System.out.println("Single Room : Press 1");
                                System.out.println("Double Room : Press 2");
                                System.out.println("Suite       : Press 3");
                                System.out.println("Back        : Press 0");
                                int addRoomMenuChoose;
                                addRoomMenuChoose = sc.nextInt();
                                while (!((addRoomMenuChoose == 1) || (addRoomMenuChoose == 2) || (addRoomMenuChoose == 3) || (addRoomMenuChoose == 0))) {
                                    System.out.println(ConsoleColor.RED + "Invalid number" + ConsoleColor.YELLOW_BRIGHT);
                                    addRoomMenuChoose = sc.nextInt();
                                }
                                switch (addRoomMenuChoose) {
                                    case 0:
                                        break outer;
                                    case 1:
                                        room[roomNumberInput].setRoomType("Single Room");

                                        break;
                                    case 2:
                                        room[roomNumberInput].setRoomType("Double Room");

                                        break;
                                    case 3:
                                        room[roomNumberInput].setRoomType("Suite");
                                        break;
                                    default:
                                        System.out.println(ConsoleColor.RED + "Invalid Number!" + ConsoleColor.YELLOW_BRIGHT);
                                        break;
                                }
                                System.out.print("Price of this room: ");
                                room[roomNumberInput].setPrice(sc.nextLong());
                                System.out.println("Room info Successfully changed");
                                room[roomNumberInput].roomInfo();
                                break outer;
                            case 5://delete room
                                if (roomCounter == 0)
                                    System.out.println(ConsoleColor.RED + "There is no Room" + ConsoleColor.YELLOW_BRIGHT);
                                System.out.println("Enter -1 to Back to main menu");
                                System.out.println("Enter Room Number: ");
                                roomNumberInput = sc.nextInt();
                                if (roomNumberInput == -1) break outer;
                                while (roomNumberInput >= roomCounter || !room[roomNumberInput].isAvailable()) {
                                    System.out.print(ConsoleColor.RED + "Invalid room number!\nEnter Room number again: ");
                                    roomNumberInput = sc.nextInt();
                                    if (roomNumberInput == -1) break outer;
                                }
                                room[roomNumberInput].setAvailable(false);
                                System.out.println("Room Number '" + roomNumberInput + "' is out of service now!");
                                break outer;
                            case 6://change manager info
                                Manager tempManager;
                                tempManager = inputSingUpInfoForManager();
                                System.out.println("Do you want to replace these info?(y/n)");
                                String check = sc.next();
                                while (!check.equalsIgnoreCase("Y") && !check.equalsIgnoreCase("n")) {
                                    System.out.println(ConsoleColor.RED + "Please Enter a valid character!" + ConsoleColor.BLUE);
                                    check = sc.next();
                                }
                                if (check.equalsIgnoreCase("n")) {
                                    System.out.println("OK :)");
                                } else {
                                    manager = tempManager;
                                }
                                break outer;
                            case 7://ban User
                                if (guestCounter == 0) {
                                    System.out.println(ConsoleColor.RED + "There is no registered guest!" + ConsoleColor.GREEN);
                                    break outer;
                                }
                                System.out.println("Enter Guest's Id: ");
                                String guestIsGoingToBeBan = sc.next();
                                for (int i = 0; i < guestCounter; i++) {
                                    if (guestIsGoingToBeBan.equals(guest[i].getID())) {
                                        System.out.println("Are you sure you want to ban this guest??(y/n)");
                                        check = sc.next();
                                        while (!check.equalsIgnoreCase("Y") && !check.equalsIgnoreCase("n")) {
                                            System.out.println(ConsoleColor.RED + "Please Enter invalid character!" + ConsoleColor.BLUE);
                                            check = sc.next();
                                        }
                                        if (check.equals("y")) {
                                            guest[i].setIsBaned(true);
                                            System.out.println(guest[i].getName() + "with id " + guest[i].getID() + " is Baned!");
                                            System.out.print("Press enter to continue...");
                                            sc.nextLine();
                                        } else {
                                            System.out.print("ok :)\nPress enter to continue...");
                                            sc.nextLine();
                                        }
                                        break outer;
                                    }
                                }
                                System.out.println(ConsoleColor.RED + "Id not found!");
                                break outer;
                            case 8: //Unban user

                                if (guestCounter == 0) {
                                    System.out.println(ConsoleColor.RED + "There is no registered guest!" + ConsoleColor.GREEN);
                                    break outer;
                                }
                                System.out.println("Enter Guest's Id: ");
                                String guestIsGoingToBeUnBan = sc.next();
                                for (int i = 0; i < guestCounter; i++) {
                                    if (guestIsGoingToBeUnBan.equals(guest[i].getID())) {
                                        if(!guest[i].getIsBaned()){
                                            System.out.println("This user is not banned!");
                                            break outer;
                                        }
                                        System.out.println("Are you sure you want to Unban this guest??(y/n)");
                                        check = sc.next();
                                        while (!check.equalsIgnoreCase("Y") && !check.equalsIgnoreCase("n")) {
                                            System.out.println(ConsoleColor.RED + "Please Enter invalid character!" + ConsoleColor.BLUE);
                                            check = sc.next();
                                        }
                                        if (check.equals("y")) {
                                            guest[i].setIsBaned(false);
                                            System.out.println(guest[i].getName() + "with id " + guest[i].getID() + " is UnBaned!");
                                            System.out.print("Press enter to continue...");
                                            sc.nextLine();
                                        } else {
                                            System.out.print("ok :)\nPress enter to continue...");
                                            sc.nextLine();
                                        }
                                        break outer;
                                    }
                                }
                                System.out.println(ConsoleColor.RED + "Id not found!");
                                break outer;
                            default:
                                System.out.println("invalid number!!");
                                break outer;
                            case 9: //list of guests
                                if (guestCounter == 0) {
                                    System.out.println(ConsoleColor.RED + "There is no registered guest!" + ConsoleColor.YELLOW_BRIGHT);
                                    break outer;
                                }
                                for (int i = 0; i < guestCounter; i++) {
                                    if(guest[i].getIsBaned()){
                                        System.out.println(ConsoleColor.RED+"This User is banned"+ConsoleColor.YELLOW_BRIGHT);
                                        break outer;
                                    }
                                    guest[i].personInfoPrint();
                                    System.out.println("---------------------");
                                }
                                break outer;
                            case 10: //List of personnel
                                if (personnelCounter == 0) {
                                    System.out.println(ConsoleColor.RED + "There is no registered Personnel!" + ConsoleColor.YELLOW_BRIGHT);
                                    break outer;
                                }
                                for (int i = 0; i < personnelCounter; i++) {
                                    if(personnel[i].getIsBaned()){
                                        System.out.println(ConsoleColor.RED+"This User is banned"+ConsoleColor.YELLOW_BRIGHT);
                                        break outer;
                                    }
                                    personnel[i].personInfoPrint();
                                    System.out.println("---------------------");

                                }
                                break outer;
                            case 11: //list of rooms
                                if (roomCounter == 0) {
                                    System.out.println(ConsoleColor.RED + "There is no room!" + ConsoleColor.YELLOW_BRIGHT);
                                    break outer;
                                }
                                for (int i = 0; i < roomCounter; i++) {
                                    if(!room[i].isAvailable()){
                                        System.out.println(ConsoleColor.RED+"This Room is Deleted!"+ConsoleColor.YELLOW_BRIGHT);
                                    }
                                    room[i].roomInfo();
                                    System.out.println("---------------------");
                                }
                                break outer;
                        }


                case 4://sing up guest
                    guest[guestCounter] = inputSingUpInfoForGuest();
                    for (int i = 0; i < guestCounter; i++) {
                        while (guest[guestCounter].getID().equals(guest[i].getID())) {
                            System.out.print(ConsoleColor.RED + "ERROR!\nThis Id is taken! Tyr another one: " + ConsoleColor.YELLOW_BRIGHT);
                            guest[guestCounter].setID(sc.next());
                        }
                    }
                    guest[guestCounter].personInfoPrint();
                    guestCounter++;
                    break;
                default:
                    System.out.println("invalid number!");
                    break;
            }
        }
    }

    public static void logInMenu() {
        System.out.println(ConsoleColor.PURPLE + "Log in as:");
        System.out.println("Guest                   : Press 1");
        System.out.println("Personnel               : Press 2");
        System.out.println("Manager                 : Press 3");
        System.out.println("New Guest?! Sign up now : Press 4");
        System.out.println("Exit                    : Press 0");
    }

    public static Guest inputSingUpInfoForGuest() {
        Guest person = new Guest();
        Scanner sc = new Scanner(System.in);
        System.out.print("Name: ");
        person.setName(sc.next());

        try {
            System.out.print("Age: ");
            person.setAge(sc.nextInt());
            while (person.getAge() < 18) {
                System.out.println("Your too young!");
                System.out.print("Age: ");
                person.setAge(sc.nextInt());
            }
        } catch (Exception e) {
            System.out.println("Only numbers IDIOT!");
            System.out.println("Do The registration again!!");
            person = inputSingUpInfoForGuest();
            return person;
        }
        System.out.print("ID: ");
        person.setID(sc.next());

        System.out.print("Gender(Male/Female): ");
        person.setGender(sc.next());

        while (!person.getGender().equalsIgnoreCase("male") && !person.getGender().equalsIgnoreCase("female")) {
            System.out.println("Please Enter Invalid Info!");
            System.out.print("Gender(Male/Female): ");
            person.setGender(sc.next());
        }

        System.out.println("Enter Your Password: ");
        person.setPassword(sc.next());
        System.out.println("Re-Enter Your Password: ");
        String checkPassword = sc.next();
        while (!person.getPassword().equals(checkPassword)) {
            System.out.println(ConsoleColor.RED + "Error!\nPassword and Re-entered password doesn't match!" + ConsoleColor.BLUE);
            System.out.println("Enter Your Password: ");
            person.setPassword(sc.next());
            System.out.println("Re-Enter Your Password: ");
            checkPassword = sc.next();
        }

        System.out.println("User Successfully Registered!");
        person.personInfoPrint();
        System.out.print(ConsoleColor.BLUE_BOLD + "Confirm the Information (Y/N)? : " + ConsoleColor.BLUE);
        String check = sc.next();
        while (!check.equalsIgnoreCase("Y") && !check.equalsIgnoreCase("n")) {
            System.out.println(ConsoleColor.RED + "Please Enter invalid character!" + ConsoleColor.BLUE);
            check = sc.next();
        }
        if (check.equalsIgnoreCase("n")) {
            System.out.println("Register Again!");
            person = inputSingUpInfoForGuest();
        } else if (check.equalsIgnoreCase("y")) {
            System.out.println(ConsoleColor.BLUE_BOLD + "Welcome " + ConsoleColor.BLUE_BOLD + ConsoleColor.YELLOW_BRIGHT + person.getName() + ConsoleColor.RESET);
        }
        return person;
    }

    public static Personnel inputSingUpInfoForPersonnel() {
        Personnel person = new Personnel();
        Scanner sc = new Scanner(System.in);
        System.out.print("Name: ");
        person.setName(sc.next());

        try {
            System.out.print("Age: ");
            person.setAge(sc.nextInt());
            while (person.getAge() < 18) {
                System.out.println("Your too young!");
                System.out.print("Age: ");
                person.setAge(sc.nextInt());
            }
        } catch (Exception e) {
            System.out.println("Only numbers IDIOT!");
            System.out.println("Do The registration again!!");
            person = inputSingUpInfoForPersonnel();
            return person;
        }

        System.out.print("ID: ");
        person.setID(sc.next());


        System.out.print("Gender(Male/Female): ");
        person.setGender(sc.next());

        while (!person.getGender().equalsIgnoreCase("male") && !person.getGender().equalsIgnoreCase("female")) {
            System.out.println("Please Enter Invalid Info!");
            System.out.print("Gender(Male/Female): ");
            person.setGender(sc.next());
        }

        System.out.println("Enter Your Password: ");
        person.setPassword(sc.next());
        System.out.println("Re-Enter Your Password: ");
        String checkPassword = sc.next();
        while (!person.getPassword().equals(checkPassword)) {
            System.out.println(ConsoleColor.RED + "Error!\nPassword and Re-entered password doesn't match!" + ConsoleColor.BLUE);
            System.out.println("Enter Your Password: ");
            person.setPassword(sc.next());
            System.out.println("Re-Enter Your Password: ");
            checkPassword = sc.next();
        }
        System.out.println("User Successfully Registered!");
        person.personInfoPrint();
        System.out.print(ConsoleColor.BLUE_BOLD + "Confirm the Information (Y/N)? : " + ConsoleColor.BLUE);
        String check = sc.next();
        while (!check.equalsIgnoreCase("Y") && !check.equalsIgnoreCase("n")) {
            System.out.println(ConsoleColor.RED + "Please Enter invalid character!" + ConsoleColor.BLUE);
            check = sc.next();
        }
        if (check.equalsIgnoreCase("n")) {
            System.out.println("Register Again!");
            person = inputSingUpInfoForPersonnel();
        } else if (check.equalsIgnoreCase("y")) {
            System.out.println(ConsoleColor.BLUE_BOLD + "Welcome " + ConsoleColor.BLUE_BOLD + ConsoleColor.YELLOW_BRIGHT + person.getName() + ConsoleColor.RESET);
        }
        return person;
    }

    public static Manager inputSingUpInfoForManager() {
        Manager person = new Manager();
        Scanner sc = new Scanner(System.in);
        System.out.print("Name: ");
        person.setName(sc.next());

        try {
            System.out.print("Age: ");
            person.setAge(sc.nextInt());
            while (person.getAge() < 18) {
                System.out.println("Your too young!");
                System.out.print("Age: ");
                person.setAge(sc.nextInt());
            }
        } catch (Exception e) {
            System.out.println("Only numbers IDIOT!");
            System.out.println("Do The registration again!!");
            person = inputSingUpInfoForManager();
            return person;
        }

        System.out.print("ID: ");
        person.setID(sc.next());


        System.out.print("Gender(Male/Female): ");
        person.setGender(sc.next());

        while (!person.getGender().equalsIgnoreCase("male") && !person.getGender().equalsIgnoreCase("female")) {
            System.out.println("Please Enter Invalid Info!");
            System.out.print("Gender(Male/Female): ");
            person.setGender(sc.next());
        }

        System.out.println("Enter Your Password: ");
        person.setPassword(sc.next());
        System.out.println("Re-Enter Your Password: ");
        String checkPassword = sc.next();
        while (!person.getPassword().equals(checkPassword)) {
            System.out.println(ConsoleColor.RED + "Error!\nPassword and Re-entered password doesn't match!" + ConsoleColor.BLUE);
            System.out.println("Enter Your Password: ");
            person.setPassword(sc.next());
            System.out.println("Re-Enter Your Password: ");
            checkPassword = sc.next();
        }

        System.out.println("User Successfully Registered!");
        person.personInfoPrint();
        System.out.print(ConsoleColor.BLUE_BOLD + "Confirm the Information (Y/N)? : " + ConsoleColor.BLUE);
        String check = sc.next();
        while (!check.equalsIgnoreCase("Y") && !check.equalsIgnoreCase("n")) {
            System.out.println(ConsoleColor.RED + "Please Enter invalid character!" + ConsoleColor.BLUE);
            check = sc.next();
        }
        if (check.equalsIgnoreCase("n")) {
            System.out.println("Register Again!");
            person = inputSingUpInfoForManager();
        } else if (check.equalsIgnoreCase("y")) {
            System.out.println(ConsoleColor.BLUE_BOLD + "Welcome " + ConsoleColor.BLUE_BOLD + ConsoleColor.YELLOW_BRIGHT + person.getName() + ConsoleColor.RESET);
        }
        return person;
    }

    public static int managerMenu(Manager manager) {
        Scanner scanner = new Scanner(System.in);

        String idInput;
        String passwordInput;

        System.out.println("Enter -1 to return");
        System.out.print(ConsoleColor.GREEN + "Enter your id: ");
        idInput = scanner.next();
        if (idInput.equals("-1")) return -1;

        System.out.print("Enter your password: ");
        passwordInput = scanner.next();

        if (!manager.getPassword().equals(passwordInput) || !manager.getID().equals(idInput)) {
            System.out.println(ConsoleColor.RED + "Wrong ID or PASSWORD!" + ConsoleColor.GREEN);
            managerMenu(manager);
        } else {
            welcomeMrOrMiss(manager);
            System.out.println("Register Personnel                : Press 1");
            System.out.println("Fire Personnel                    : Press 2");
            System.out.println("Add Room                          : Press 3");
            System.out.println("Edit Room                         : Press 4");
            System.out.println("Delete Room                       : Press 5");
            System.out.println("Change manager information        : Press 6");
            System.out.println("Ban User                          : Press 7");
            System.out.println("Unban User                        : Press 8");
            System.out.println("List of guests                    : Press 9");
            System.out.println("List of personnel                 : Press 10");
            System.out.println("List of rooms                     : Press 11");
            System.out.println("Back                              : Press 0");
        }
        return 1;
    }

    public static Room addRoom() {
        Room tempRoom = new Room();
        Scanner sc = new Scanner(System.in);
        System.out.println(ConsoleColor.YELLOW_BRIGHT + "Room Type: ");
        System.out.println("Single Room : Press 1");
        System.out.println("Double Room : Press 2");
        System.out.println("Suite       : Press 3");
        System.out.println("Back        : Press 0");
        int addRoomMenuChoose;
        addRoomMenuChoose = sc.nextInt();
        while (!((addRoomMenuChoose == 1) || (addRoomMenuChoose == 2) || (addRoomMenuChoose == 3) || (addRoomMenuChoose == 0))) {
            System.out.println(ConsoleColor.RED + "Invalid number" + ConsoleColor.YELLOW_BRIGHT);
            addRoomMenuChoose = sc.nextInt();
        }
        switch (addRoomMenuChoose) {
            case 0:
                return null;
            case 1:
                tempRoom.setRoomType("Single Room");
                setRoomInfoForAdding(tempRoom);
                break;
            case 2:
                tempRoom.setRoomType("Double Room");
                setRoomInfoForAdding(tempRoom);
                break;
            case 3:
                tempRoom.setRoomType("Suite");
                setRoomInfoForAdding(tempRoom);
                break;
            default:
                System.out.println(ConsoleColor.RED + "Invalid Number!" + ConsoleColor.YELLOW_BRIGHT);
                break;
        }

        return tempRoom;
    }


    public static void setRoomInfoForAdding(Room tempRoom) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Price of this room: ");
        tempRoom.setPrice(sc.nextLong());
        System.out.println("Room successfully added");
        System.out.println("Room type: " + tempRoom.getRoomType());
        System.out.println("Room number: " + roomCounter);
        System.out.println("Price Per Night : " + tempRoom.getPrice());
        System.out.println("Confirm?! (y/n)");
        String check = sc.next();
        while (!(check.equalsIgnoreCase("y") || check.equalsIgnoreCase("n"))) {
            System.out.println(ConsoleColor.RED + "Invalid input!!" + ConsoleColor.YELLOW_BRIGHT);
            check = sc.next();
        }
        if (check.equalsIgnoreCase("n")) {
            System.out.println("Try Again!");
            addRoom();
        } else {
            roomCounter++;
        }


    }

    public static int guestMenu() {
        Scanner scanner = new Scanner(System.in);

        String idInput;
        String passwordInput;

        System.out.println(ConsoleColor.CYAN_BRIGHT+"Enter -1 to return");
        System.out.print( "Enter your id: ");
        idInput = scanner.next();
        if (idInput.equals("-1")) return -1; //back to main menu
        for (int i = 0; i < guestCounter; i++) {
            if(guest[i].getID().equals(idInput)){
                if (guest[i].getIsBaned()){
                    System.out.println("You Are Banned!");
                    return -2;
                }
                System.out.print("Enter your password: ");
                passwordInput = scanner.next();

                if (!guest[i].getPassword().equals(passwordInput) || !guest[i].getID().equals(idInput)) {
                    System.out.println(ConsoleColor.RED + "Wrong PASSWORD!" + ConsoleColor.CYAN_BRIGHT);
                    guestMenu();
                } else {
                    welcomeMrOrMiss(guest[i]);
                    System.out.println("Reserve Room       : Press 1");
                    System.out.println("Cancel Reservation : Press 2");
                    System.out.println("Edit info          : Press 3 ");
                    System.out.println("See info           : Press 4");
                    System.out.println("Back               : Press 0");
                    return i;
                }
            }

        }
        System.out.println(ConsoleColor.RED+"Id not found!"+ConsoleColor.CYAN_BRIGHT);
        guestMenu();
        return -3;
    }
    public static void welcomeMrOrMiss(Person p){
        if (p.getGender().equalsIgnoreCase("male")) {
            System.out.println("Welcome MR." + p.getName());
        } else {
            System.out.println("Welcome Miss." + p.getName());
        }

    }
    public static void reserveRoom(int guestWhoReserved){
        Scanner sc = new Scanner(System.in);
        System.out.println(ConsoleColor.CYAN + "Choose the type of Room that you want: ");
        System.out.println("Single Room : Press 1");
        System.out.println("Double Room : Press 2");
        System.out.println("Suite       : Press 3");
        System.out.println("Back        : Press 0");
        int reserveRoomChoose = sc.nextInt();
            switch (reserveRoomChoose) {
                case 0:
                    return;
                case 1:

                    for (int i = 0; i < roomCounter; i++) {
                        if (room[i].isEmpty() && room[i].getRoomType().equalsIgnoreCase("Single room") && room[i].isAvailable()) {
                            System.out.println("Price of this room: " + room[i].getPrice());
                            System.out.println("Would you like to reserve this room?(y/n): ");
                            String checkAnswer = sc.next();
                            while (!checkAnswer.equalsIgnoreCase("Y") && !checkAnswer.equalsIgnoreCase("n")) {
                                System.out.println(ConsoleColor.RED + "Please Enter invalid character!" + ConsoleColor.CYAN_BRIGHT);
                                checkAnswer = sc.next();
                            }
                               if (checkAnswer.equalsIgnoreCase("y")) {
                                System.out.println("Your room number is '" + i + "'");
                                room[i].setGuestReservedThisRoomIndex(guestWhoReserved);
                                room[i].setEmpty(false);

                                return;
                            }

                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < roomCounter; i++) {
                        if (room[i].isEmpty() && room[i].getRoomType().equalsIgnoreCase("Double room") && room[i].isAvailable()) {
                            System.out.println("Price of this room: " + room[i].getPrice());
                            System.out.println("Would you like to reserve this room?(y/n): ");
                            String checkAnswer = sc.next();
                            while (!checkAnswer.equalsIgnoreCase("Y") && !checkAnswer.equalsIgnoreCase("n")) {
                                System.out.println(ConsoleColor.RED + "Please Enter invalid character!" + ConsoleColor.CYAN_BRIGHT);
                                checkAnswer = sc.next();
                            }
                            if (checkAnswer.equalsIgnoreCase("y")) {
                                System.out.println("Your room number is '" + i + "'");
                                room[i].setGuestReservedThisRoomIndex(guestWhoReserved);
                                room[i].setEmpty(false);
                                return;
                            }

                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < roomCounter; i++) {
                        if (room[i].isEmpty() && room[i].getRoomType().equalsIgnoreCase("Suite") && room[i].isAvailable()) {
                            System.out.println("Price of this room: " + room[i].getPrice());
                            System.out.println("Would you like to reserve this room?(y/n): ");
                            String checkAnswer = sc.next();
                            while (!checkAnswer.equalsIgnoreCase("Y") && !checkAnswer.equalsIgnoreCase("n")) {
                                System.out.println(ConsoleColor.RED + "Please Enter invalid character!" + ConsoleColor.CYAN_BRIGHT);
                                checkAnswer = sc.next();
                            }

                              if (checkAnswer.equalsIgnoreCase("y")) {
                                System.out.println("Your room number is '" + i + "'");
                                room[i].setGuestReservedThisRoomIndex(guestWhoReserved);
                                room[i].setEmpty(false);
                                return;
                            }
                        }
                    }
                    break;
            }
            System.out.println("Sorry, There is no available room right now!!");


        }
        public static int personnelLogin(){
            Scanner scanner = new Scanner(System.in);

            String idInput;
            String passwordInput;
            System.out.println(ConsoleColor.WHITE_BRIGHT+"Enter -1 to return");
            System.out.print( "Enter your id: ");
            idInput = scanner.next();
            if (idInput.equals("-1")) return -1; //back to main menu
            for (int i = 0; i < personnelCounter; i++) {
                if(personnel[i].getID().equals(idInput)){
                    if (personnel[i].getIsBaned()){
                        System.out.println(ConsoleColor.RED+"You Are Banned!"+ConsoleColor.WHITE_BRIGHT);
                        return -2;
                    }
                    System.out.print("Enter your password: ");
                    passwordInput = scanner.next();

                    if (!personnel[i].getPassword().equals(passwordInput) || !personnel[i].getID().equals(idInput)) {
                        System.out.println(ConsoleColor.RED + "Wrong PASSWORD!" + ConsoleColor.WHITE_BRIGHT);
                        personnelLogin();
                    } else {
                        welcomeMrOrMiss(personnel[i]);
                        System.out.println("See info : Press 1");
                        System.out.println("Back     : Press 0");
                        return i;
                    }
                }

            }
            System.out.println(ConsoleColor.RED+"Id not found!"+ConsoleColor.WHITE_BRIGHT);
            return personnelLogin();
        }
}


