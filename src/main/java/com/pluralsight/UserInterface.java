System.out.println("Would the customer like any add-ons?");
System.out.println("1. None");
System.out.println("2. Warranty ($1200)");
System.out.println("3. Detailing ($200)");
System.out.println("4. Rustproofing ($800)");
System.out.println("5. Full Package (Warranty + Detailing + Rustproofing) ($2000)");
System.out.print("Enter option: ");
String addOnOption = scanner.nextLine();

if (contract instanceof SalesContract sc) {
        switch (addOnOption) {
        case "2" -> sc.addAddOn(new AddOn("Warranty", 1200));
        case "3" -> sc.addAddOn(new AddOn("Detailing", 200));
        case "4" -> sc.addAddOn(new AddOn("Rustproofing", 800));
        case "5" -> {
        sc.addAddOn(new AddOn("Warranty", 1200));
        sc.addAddOn(new AddOn("Detailing", 200));
        sc.addAddOn(new AddOn("Rustproofing", 800));
        }
default -> {}
        }

        new ContractFileManager().saveContract(sc);
}
