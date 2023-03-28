
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WeekendFitness {

    public static List<Timetable> timeTableList = new ArrayList<>();
    public static List<Customer> customerList = new ArrayList<>();
    public static List<Ratings> ratingList = new ArrayList<>();
    public static List<MonthlyReportData> monthlyReportDataList = new ArrayList<>();
    public static List<MonthlyFitnessReportData> monthlyFitnessReportData = new ArrayList<>();
    public static String name;

    public static void main(String[] args) {
        FileStorage fs = new FileStorage();
        timeTableList = fs.readTimetable();
        customerList = fs.readCustomers();
        ratingList = fs.readRatings();
        monthlyReportDataList = fs.readMonthlyReportData();
        monthlyFitnessReportData = fs.readMonthlyFitnessReportData();
        selectMainMenu();
    }

    private static int mainMenu() {
        int option = 0;
        System.out.print("\nEnter you name: ");
        Scanner myObj = new Scanner(System.in);
        name = myObj.nextLine();
        System.out.println("\n1. Book a group fitness lesson");
        System.out.println("2. Change/Cancel a booking");
        System.out.println("3. Attend a lesson");
        System.out.println("4. Monthly lesson report");
        System.out.println("5. Monthly champion fitness type report");
        System.out.println("0. Exit");
        System.out.print("Please select: ");
        if (myObj.hasNextInt()) {
            option = myObj.nextInt();
        } else {
            System.out.println("Please select valid option\n");
        }
        return option;
    }

    private static void selectMainMenu() {
        boolean takeInput = true;
        while (takeInput) {
            int option = mainMenu();

            if (option == 1) {
                int maxBookingId;
                Customer customer = customerList.stream().filter(c -> name.equals(c.getCustomerName())).findAny().orElse(null);
                if (customer == null) {
                    maxBookingId = 1;
                } else {
                    maxBookingId = customer.getClasses().stream().mapToInt(Classes::getBookingId).max().orElse(0) + 1;
                }
                bookGroupFitnessLesson(maxBookingId);
            } else if (option == 2) {
                changeOrCancelBooking();
            } else if (option == 3) {
                attendLesson();
            } else if (option == 4) {
                monthlyLessonReport();
            } else if (option == 5) {
                monthlyFitnessTypeReport();
            } else if (option == 0) {
                System.out.println("Thank you. Good bye. ");
                takeInput = false;
            } else {
                System.out.println("Please select valid option\n");
            }
        }
    }

    private static void monthlyFitnessTypeReport() {
        boolean takeInput = true;
        int option = 0;
        while (takeInput) {
            Scanner myObj = new Scanner(System.in);
            System.out.println("\nSelect monthly report");
            System.out.println("1. First Month");
            System.out.println("2. Second Month");
            System.out.println("0. Exit");
            System.out.print("Please select: ");
            if (myObj.hasNextInt()) {
                option = myObj.nextInt();
                if (option == 1) {
                    System.out.println("FitnessType\t\tTotal Income");
                    List<MonthlyFitnessReportAccumulative> accumulatives = FillReport();
                    for (MonthlyFitnessReportData report : monthlyFitnessReportData) {
                        if (report.getTimeTableId() == 1
                                || report.getTimeTableId() == 2
                                || report.getTimeTableId() == 3
                                || report.getTimeTableId() == 4
                                || report.getTimeTableId() == 9
                                || report.getTimeTableId() == 10
                                || report.getTimeTableId() == 11
                                || report.getTimeTableId() == 12) {

                            MonthlyFitnessReportAccumulative fitness = accumulatives.stream().filter(f -> f.getFitnessType().equals(report.getFitnessType())).findAny().orElse(null);
                            MonthlyFitnessReportAccumulative newFitness = new MonthlyFitnessReportAccumulative();
                            newFitness.setFitnessType(report.getFitnessType());
                            newFitness.setTotalPrice(fitness.getTotalPrice() + report.getPrice());
                            accumulatives.remove(fitness);
                            accumulatives.add(newFitness);
                        }
                    }
                    accumulatives.sort(Comparator.comparing(MonthlyFitnessReportAccumulative::getTotalPrice).reversed());
                    for(MonthlyFitnessReportAccumulative m : accumulatives) {
                        System.out.println(m.getFitnessType() + "\t\t\t\t" + m.getTotalPrice());
                    }
                } else if (option == 2) {
                    System.out.println("FitnessType\t\tTotal Income\t\t");
                    List<MonthlyFitnessReportAccumulative> accumulatives = FillReport();
                    for (MonthlyFitnessReportData report : monthlyFitnessReportData) {
                        if (report.getTimeTableId() == 5
                                || report.getTimeTableId() == 6
                                || report.getTimeTableId() == 7
                                || report.getTimeTableId() == 8
                                || report.getTimeTableId() == 13
                                || report.getTimeTableId() == 14
                                || report.getTimeTableId() == 15
                                || report.getTimeTableId() == 16) {

                            MonthlyFitnessReportAccumulative fitness = accumulatives.stream().filter(f -> f.getFitnessType().equals(report.getFitnessType())).findAny().orElse(null);
                            MonthlyFitnessReportAccumulative newFitness = new MonthlyFitnessReportAccumulative();
                            newFitness.setFitnessType(report.getFitnessType());
                            newFitness.setTotalPrice(fitness.getTotalPrice() + report.getPrice());
                            accumulatives.remove(fitness);
                            accumulatives.add(newFitness);
                        }
                    }
                    accumulatives.sort(Comparator.comparing(MonthlyFitnessReportAccumulative::getTotalPrice).reversed());
                    for(MonthlyFitnessReportAccumulative m : accumulatives) {
                        System.out.println(m.getFitnessType() + "\t\t\t\t" + m.getTotalPrice());
                    }
                } else if (option == 0) {
                    takeInput = false;
                } else {
                    System.out.println("Please select valid option\n");
                }
            } else {
                System.out.println("Please select valid option\n");
            }
        }
    }

    private static List<MonthlyFitnessReportAccumulative> FillReport() {
        List<MonthlyFitnessReportAccumulative> accumulativeList = new ArrayList<>();

        MonthlyFitnessReportAccumulative accumulative = new MonthlyFitnessReportAccumulative();
        accumulative.setFitnessType(Constants.FITNESS_TYPE_AQUACISE);
        accumulative.setTotalPrice(0);
        accumulativeList.add(accumulative);

        accumulative = new MonthlyFitnessReportAccumulative();
        accumulative.setFitnessType(Constants.FITNESS_TYPE_SPIN);
        accumulative.setTotalPrice(0);
        accumulativeList.add(accumulative);

        accumulative = new MonthlyFitnessReportAccumulative();
        accumulative.setFitnessType(Constants.FITNESS_TYPE_YOGA);
        accumulative.setTotalPrice(0);
        accumulativeList.add(accumulative);

        accumulative = new MonthlyFitnessReportAccumulative();
        accumulative.setFitnessType(Constants.FITNESS_TYPE_BODYSCULPT);
        accumulative.setTotalPrice(0);
        accumulativeList.add(accumulative);

        accumulative = new MonthlyFitnessReportAccumulative();
        accumulative.setFitnessType(Constants.FITNESS_TYPE_BOX_FIT);
        accumulative.setTotalPrice(0);
        accumulativeList.add(accumulative);

        accumulative = new MonthlyFitnessReportAccumulative();
        accumulative.setFitnessType(Constants.FITNESS_TYPE_ZUMBA);
        accumulative.setTotalPrice(0);
        accumulativeList.add(accumulative);

        return accumulativeList;
    }

    private static void monthlyLessonReport() {
        boolean takeInput = true;
        int option = 0;
        while (takeInput) {
            Scanner myObj = new Scanner(System.in);
            System.out.println("\nSelect monthly report");
            System.out.println("1. First Month");
            System.out.println("2. Second Month");
            System.out.println("0. Exit");
            System.out.print("Please select: ");
            if (myObj.hasNextInt()) {
                option = myObj.nextInt();
                if (option == 1) {
                    System.out.println("Weekend\t\tFitnessType\t\tTotal Classes\t\tAverage Rating");
                    for (MonthlyReportData report : monthlyReportDataList) {
                        if (report.getTimeTableId() == 1
                                || report.getTimeTableId() == 2
                                || report.getTimeTableId() == 3
                                || report.getTimeTableId() == 4
                                || report.getTimeTableId() == 9
                                || report.getTimeTableId() == 10
                                || report.getTimeTableId() == 11
                                || report.getTimeTableId() == 12) {
                            Timetable tt = timeTableList.stream().filter(t -> t.getTimetableId() == report.getTimeTableId()).findAny().orElse(null);
                            if (report.getClassNumber() == 1) {
                                System.out.println(tt.getWeekend() + "\t\t" + tt.getFirstExerciseType() + "\t\t\t" + report.getTotalClasses() + "\t\t\t\t\t" + (report.getTotalRatings() / report.getTotalClasses()));
                            } else if (report.getClassNumber() == 2) {
                                System.out.println(tt.getWeekend() + "\t\t" + tt.getSecondExerciseType() + "\t\t\t" + report.getTotalClasses() + "\t\t\t\t\t" + (report.getTotalRatings() / report.getTotalClasses()));
                            }
                        }
                    }
                } else if (option == 2) {
                    System.out.println("Weekend\t\tFitnessType\t\tTotal Classes\t\tAverage Rating");
                    for (MonthlyReportData report : monthlyReportDataList) {
                        if (report.getTimeTableId() == 5
                                || report.getTimeTableId() == 6
                                || report.getTimeTableId() == 7
                                || report.getTimeTableId() == 8
                                || report.getTimeTableId() == 13
                                || report.getTimeTableId() == 14
                                || report.getTimeTableId() == 15
                                || report.getTimeTableId() == 16) {
                            Timetable tt = timeTableList.stream().filter(t -> t.getTimetableId() == report.getTimeTableId()).findAny().orElse(null);
                            if (report.getClassNumber() == 1) {
                                System.out.println(tt.getWeekend() + "\t\t" + tt.getFirstExerciseType() + "\t\t\t" + report.getTotalClasses() + "\t\t\t\t\t" + (report.getTotalRatings() / report.getTotalClasses()));
                            } else if (report.getClassNumber() == 2) {
                                System.out.println(tt.getWeekend() + "\t\t" + tt.getSecondExerciseType() + "\t\t\t" + report.getTotalClasses() + "\t\t\t\t\t" + (report.getTotalRatings() / report.getTotalClasses()));
                            }
                        }
                    }
                } else if (option == 0) {
                    takeInput = false;
                } else {
                    System.out.println("Please select valid option\n");
                }
            } else {
                System.out.println("Please select valid option\n");
            }
        }
    }

    private static void getCustomerForReport(List<Timetable> timetables) {
        for (Customer cust : customerList) {
            for (Classes c : cust.getClasses()) {
                if (c.getStatus().equals(Constants.BOOKING_STATUS_ATTENDED)) {

                }
            }
        }
    }

    private static List<Timetable> getTimeTableForReport(int month) {
        List<Timetable> timetables = new ArrayList<>();
        List<Timetable> timetablesForReport = new ArrayList<>();
        if (month == 1) {
            timetables = timeTableList.stream().filter(t -> t.getWeekend().equals("Weekend 1") || t.getWeekend().equals("Weekend 2") || t.getWeekend().equals("Weekend 3") || t.getWeekend().equals("Weekend 4")).collect(Collectors.toList());
        } else if (month == 2) {
            timetables = timeTableList.stream().filter(t -> t.getWeekend().equals("Weekend 5") || t.getWeekend().equals("Weekend 6") || t.getWeekend().equals("Weekend 7") || t.getWeekend().equals("Weekend 8")).collect(Collectors.toList());
        }

        for (Timetable t : timetables) {
            if (t.getFirstRemainingSeats() < 5 || t.getSecondRemainingSeats() < 5) {
                timetablesForReport.add(t);
            }
        }
        return timetablesForReport;
    }

    private static void attendLesson() {
        Customer customer = customerList.stream().filter(c -> name.equals(c.getCustomerName())).findAny().orElse(null);
        if (customer == null) {
            System.out.println("No bookings found");
        } else {
            boolean takeInput = true;
            int option = 0;
            while (takeInput) {

                Scanner myObj = new Scanner(System.in);
                System.out.println("\nSelect lesson you want to attend");

                System.out.println("Booking Id\t\tWeekend\t\t\tDay\t\t\tFitness Lesson\t\tPrice");
                for (Classes classes : customer.getClasses()) {
                    Timetable tt = timeTableList.stream().filter(t -> t.getTimetableId() == classes.getTimetableId()).findAny().orElse(null);
                    if (tt != null) {
                        if (classes.getClassNumber() == 1 && classes.getStatus().equals(Constants.BOOKING_STATUS_BOOKED)) {
                            System.out.println(classes.getBookingId() + "\t\t\t\t" + tt.getWeekend() + "\t\t" + tt.getDay() + "\t\t" + tt.getFirstExerciseType() + "\t\t\t$" + tt.getFirstPrice());
                        } else if (classes.getClassNumber() == 2 && classes.getStatus().equals(Constants.BOOKING_STATUS_BOOKED)) {
                            System.out.println(classes.getBookingId() + "\t\t\t\t" + tt.getWeekend() + "\t\t" + tt.getDay() + "\t\t\t" + tt.getSecondExerciseType() + "\t\t\t$" + tt.getSecondPrice());
                        }
                    }
                }
                System.out.println("0. \t\t\t\tExit");
                System.out.print("Please select: ");
                if (myObj.hasNextInt()) {
                    option = myObj.nextInt();

                    if (option >= 1) {
                        attendBooking(option);
                        giveRating(option);
                        System.out.println("Lesson is attended\n");
                        takeInput = false;
                    } else if (option == 0) {
                        takeInput = false;
                    } else {
                        System.out.println("Please select valid option\n");
                    }
                } else {
                    System.out.println("Please select valid option\n");
                }
            }
        }
    }

    private static void giveRating(int bookingId) {
        Scanner myObj = new Scanner(System.in);
        System.out.print("\nGive review: ");
        String review = myObj.nextLine();
        System.out.print("\nGive rating from 1 to 5: ");
        int rating = myObj.nextInt();

        Ratings ratings = new Ratings();
        ratings.setRating(rating);
        ratings.setName(name);
        ratings.setReview(review);
        ratings.setBookingId(bookingId);
        ratingList.add(ratings);

        FileStorage fs = new FileStorage();
        fs.writeRatings(ratingList);

        Customer customer = customerList.stream().filter(c -> name.equals(c.getCustomerName())).findAny().orElse(null);
        if (customer != null && customer.getClasses() != null) {
            for (Classes c : customer.getClasses()) {
                if (c.getBookingId() == bookingId) {

                    // monthly report start
                    MonthlyReportData report = monthlyReportDataList.stream().filter(r -> r.getTimeTableId() == c.getTimetableId() && r.getClassNumber() == c.getClassNumber()).findAny().orElse(null);
                    if (report == null) {
                        MonthlyReportData data = new MonthlyReportData();
                        data.setTimeTableId(c.getTimetableId());
                        data.setClassNumber(c.getClassNumber());
                        data.setTotalClasses(1);
                        data.setTotalRatings(rating);
                        monthlyReportDataList.add(data);
                    } else {
                        int index = IntStream.range(0, monthlyReportDataList.size())
                                .filter(counter -> monthlyReportDataList.get(counter).getTimeTableId() == c.getTimetableId() && monthlyReportDataList.get(counter).getClassNumber() == c.getClassNumber())
                                .findFirst()
                                .orElse(-1);
                        monthlyReportDataList.get(index).setTotalRatings(monthlyReportDataList.get(index).getTotalRatings() + rating);
                        monthlyReportDataList.get(index).setTotalClasses(monthlyReportDataList.get(index).getTotalClasses() + 1);
                    }
                    fs.writeMonthlyReportData(monthlyReportDataList);
                    // monthly report end

                    // monthly fitness report start
                    Timetable timetable = timeTableList.stream().filter(t -> t.getTimetableId() == c.getTimetableId()).findAny().orElse(null);
                    if (c.getClassNumber() == 1) {
                        MonthlyFitnessReportData fitness = new MonthlyFitnessReportData();
                        fitness.setTimeTableId(c.getTimetableId());
                        fitness.setFitnessType(timetable.getFirstExerciseType());
                        fitness.setPrice(timetable.getFirstPrice());
                        monthlyFitnessReportData.add(fitness);
                    } else if (c.getClassNumber() == 2) {
                        MonthlyFitnessReportData fitness = new MonthlyFitnessReportData();
                        fitness.setTimeTableId(c.getTimetableId());
                        fitness.setFitnessType(timetable.getSecondExerciseType());
                        fitness.setPrice(timetable.getSecondPrice());
                        monthlyFitnessReportData.add(fitness);
                    }
                    fs.writeMonthlyFitnessReportData(monthlyFitnessReportData);
                    // monthly fitness report end
                }
            }
        }
    }

    private static void attendBooking(int bookingId) {
        int counter = 0;
        for (Customer customer : customerList) {
            int counter2 = 0;
            for (Classes c : customer.getClasses()) {
                if (c.getBookingId() == bookingId) {
                    customerList.get(counter).getClasses().get(counter2).setStatus(Constants.BOOKING_STATUS_ATTENDED);
                    FileStorage fs = new FileStorage();
                    fs.writeCustomers(customerList);
                    break;
                }
                counter2++;
            }
            counter++;
        }
    }

    private static void changeOrCancelBooking() {

        Customer customer = customerList.stream().filter(c -> name.equals(c.getCustomerName())).findAny().orElse(null);
        if (customer == null) {
            System.out.println("No bookings found");
        } else {
            boolean takeInput = true;
            int option = 0;
            while (takeInput) {

                Scanner myObj = new Scanner(System.in);
                System.out.println("\nSelect booking you want to change");

                System.out.println("Booking Id\t\tWeekend\t\t\tDay\t\t\tFitness Lesson\t\tPrice");
                for (Classes classes : customer.getClasses()) {
                    Timetable tt = timeTableList.stream().filter(t -> t.getTimetableId() == classes.getTimetableId()).findAny().orElse(null);
                    if (tt != null) {
                        if (classes.getClassNumber() == 1) {
                            System.out.println(classes.getBookingId() + "\t\t\t\t" + tt.getWeekend() + "\t\t" + tt.getDay() + "\t\t" + tt.getFirstExerciseType() + "\t\t\t$" + tt.getFirstPrice());
                        } else {
                            System.out.println(classes.getBookingId() + "\t\t\t\t" + tt.getWeekend() + "\t\t" + tt.getDay() + "\t\t\t" + tt.getSecondExerciseType() + "\t\t\t$" + tt.getSecondPrice());
                        }
                    }
                }
                System.out.println("0. \t\t\t\tExit");
                System.out.print("Please select: ");
                if (myObj.hasNextInt()) {
                    option = myObj.nextInt();

                    if (option >= 1) {
                        cancelBooking(option);
                        bookGroupFitnessLesson(customer.getClasses().stream().mapToInt(Classes::getBookingId).max().orElse(0) + 1);
                        takeInput = false;
                    } else if (option == 0) {
                        takeInput = false;
                    } else {
                        System.out.println("Please select valid option\n");
                    }
                } else {
                    System.out.println("Please select valid option\n");
                }
            }
        }
    }

    private static void cancelBooking(int bookingId) {
        int counter = 0;
        int timeTableId = 0;
        int classNumber = 0;
        for (Customer customer : customerList) {
            int counter2 = 0;
            for (Classes c : customer.getClasses()) {
                if (c.getBookingId() == bookingId) {
                    timeTableId = c.getTimetableId();
                    classNumber = c.getClassNumber();
                    customerList.get(counter).getClasses().get(counter2).setStatus(Constants.BOOKING_STATUS_CANCELLED);
                    break;
                }
                counter2++;
            }
            counter++;
        }

        counter = 0;
        for (Timetable t : timeTableList) {
            if (t.getTimetableId() == timeTableId) {
                if (classNumber == 1) {
                    timeTableList.get(counter).setFirstRemainingSeats(timeTableList.get(counter).getFirstRemainingSeats() + 1);
                } else if (classNumber == 2) {
                    timeTableList.get(counter).setSecondRemainingSeats(timeTableList.get(counter).getSecondRemainingSeats() + 1);
                }
            }
            counter++;
        }
    }

    private static void bookGroupFitnessLesson(int bookingId) {
        boolean takeInput = true;
        int option = 0;
        while (takeInput) {
            Scanner myObj = new Scanner(System.in);
            System.out.println("\n1. View timetable by day");
            System.out.println("2. View timetable by fitness type");
            System.out.println("0. Exit");
            System.out.print("Please select: ");
            if (myObj.hasNextInt()) {
                option = myObj.nextInt();

                if (option == 1) {
                    viewTimetableByDay(bookingId);
                    takeInput = false;
                } else if (option == 2) {
                    viewTimetableByFitnessType(bookingId);
                    takeInput = false;
                } else if (option == 0) {
                    takeInput = false;
                } else {
                    System.out.println("Please select valid option\n");
                }
            } else {
                System.out.println("Please select valid option\n");
            }
        }
    }

    private static void viewTimetableByFitnessType(int bookingId) {
        boolean takeInput = true;
        int option = 0;
        while (takeInput) {
            Scanner myObj = new Scanner(System.in);
            System.out.println("\n1. Spin");
            System.out.println("2. Yoga");
            System.out.println("3. Bodysculpt");
            System.out.println("4. Zumba");
            System.out.println("5. Aquacise");
            System.out.println("6. Box Fit");
            System.out.println("0. Exit");
            System.out.print("Please select: ");
            if (myObj.hasNextInt()) {
                option = myObj.nextInt();

                if (option == 1) {
                    viewTimetableByFitness("Spin", bookingId);
                    takeInput = false;
                } else if (option == 2) {
                    viewTimetableByFitness("Yoga", bookingId);
                    takeInput = false;
                } else if (option == 3) {
                    viewTimetableByFitness("Bodysculpt", bookingId);
                    takeInput = false;
                } else if (option == 4) {
                    viewTimetableByFitness("Zumba", bookingId);
                    takeInput = false;
                } else if (option == 5) {
                    viewTimetableByFitness("Aquacise", bookingId);
                    takeInput = false;
                } else if (option == 6) {
                    viewTimetableByFitness("Box Fit", bookingId);
                    takeInput = false;
                } else if (option == 0) {
                    takeInput = false;
                } else {
                    System.out.println("Please select valid option\n");
                }
            } else {
                System.out.println("Please select valid option\n");
            }
        }
    }

    private static void viewTimetableByFitness(String fitnessType, int bookingId) {
        boolean takeInput = true;
        int option = 0;
        while (takeInput) {
            Scanner myObj = new Scanner(System.in);
            System.out.println("Sr.No\tWeekend\t\t\tDay\t\t1st Fitness Lesson\t\tPrice\t\t2nd Fitness Lesson\t\tPrice");
            for (Timetable tt : timeTableList) {
                if (tt.getFirstExerciseType().equals(fitnessType) || tt.getSecondExerciseType().equals(fitnessType)) {
                    if (tt.getFirstExerciseType().equals("Spin")) {
                        System.out.println(tt.getTimetableId() + "\t\t" + tt.getWeekend() + "\t\t" + tt.getDay() + "\t\t" + tt.getFirstExerciseType() + "\t\t\t\t$" + tt.getFirstPrice() + "\t\t\t" + tt.getSecondExerciseType() + "\t\t\t\t$" + tt.getSecondPrice());
                    } else {
                        System.out.println(tt.getTimetableId() + "\t\t" + tt.getWeekend() + "\t\t" + tt.getFirstExerciseType() + "\t\t\t$" + tt.getFirstPrice() + "\t\t\t" + tt.getSecondExerciseType() + "\t\t\t\t$" + tt.getSecondPrice());
                    }
                }
            }
            System.out.println("0. \t\tExit");
            System.out.print("Please select: ");
            if (myObj.hasNextInt()) {
                option = myObj.nextInt();

                if (option >= 1 && option <= 16) {
                    selectLesson(option, bookingId);
                    takeInput = false;
                } else if (option == 0) {
                    takeInput = false;
                } else {
                    System.out.println("Please select valid option\n");
                }
            } else {
                System.out.println("Please select valid option\n");
            }
        }
    }

    private static void bookLesson(int serialNumber, int lessonNumber, int bookingId) {
        int counter = 0;
        for (Timetable tt : timeTableList) {
            if (tt.getTimetableId() == serialNumber) {

                Customer customer = customerList.stream().filter(c -> name.equals(c.getCustomerName())).findAny().orElse(null);
                if (customer != null) {
                    Classes classes = customer.getClasses().stream().filter(cc -> cc.getTimetableId() == serialNumber && cc.getClassNumber() == lessonNumber).findAny().orElse(null);
                    if (classes != null) {
                        System.out.println("Your lesson is already booked\n");
                    } else {
                        Classes newClass = new Classes();
                        newClass.setBookingId(bookingId);
                        newClass.setTimetableId(serialNumber);
                        newClass.setStatus(Constants.BOOKING_STATUS_BOOKED);
                        newClass.setClassNumber(lessonNumber);
                        int count = 0;
                        for (Customer c : customerList) {
                            if (c.getCustomerName().equals(name)) {
                                List<Classes> classList = customerList.get(count).getClasses();
                                classList.add(newClass);
                                customerList.get(count).setClasses(classList);

                                if (lessonNumber == 1) {
                                    timeTableList.get(counter).setFirstRemainingSeats(timeTableList.get(counter).getFirstRemainingSeats() - 1);
                                } else if (lessonNumber == 2) {
                                    timeTableList.get(counter).setSecondRemainingSeats(timeTableList.get(counter).getSecondRemainingSeats() - 1);
                                }

                                FileStorage fs = new FileStorage();
                                fs.writeTimetable(timeTableList);
                                fs.writeCustomers(customerList);
                                System.out.println("Your lesson is booked\n");
                            }
                            count++;
                        }
                    }
                } else {
                    List<Classes> classList = new ArrayList<>();
                    Classes newClass = new Classes();
                    newClass.setBookingId(bookingId);
                    newClass.setTimetableId(serialNumber);
                    newClass.setClassNumber(lessonNumber);
                    newClass.setStatus(Constants.BOOKING_STATUS_BOOKED);
                    classList.add(newClass);

                    Customer cus = new Customer();
                    cus.setCustomerName(name);
                    cus.setClasses(classList);
                    customerList.add(cus);

                    if (lessonNumber == 1) {
                        timeTableList.get(counter).setFirstRemainingSeats(timeTableList.get(counter).getFirstRemainingSeats() - 1);
                    } else if (lessonNumber == 2) {
                        timeTableList.get(counter).setSecondRemainingSeats(timeTableList.get(counter).getSecondRemainingSeats() - 1);
                    }

                    FileStorage fs = new FileStorage();
                    fs.writeTimetable(timeTableList);
                    fs.writeCustomers(customerList);
                    System.out.println("Your lesson is booked\n");
                }
            }
            counter++;
        }
    }

    private static void selectLesson(int serialNumber, int bookingId) {
        boolean takeInput = true;
        int option = 0;
        while (takeInput) {
            Scanner myObj = new Scanner(System.in);
            System.out.println("\n1. First Lesson");
            System.out.println("2. Second Lesson");
            System.out.println("0. Exit");
            System.out.print("Please select: ");
            if (myObj.hasNextInt()) {
                option = myObj.nextInt();

                if (option == 1) {
                    Timetable timetable = timeTableList.stream().filter(tt -> serialNumber == tt.getTimetableId()).findAny().orElse(null);
                    if (timetable != null && timetable.getFirstRemainingSeats() == 0) {
                        System.out.println("Not enough seats available\n");
                        takeInput = false;
                    } else if (timetable != null && timetable.getFirstRemainingSeats() > 0) {
                        bookLesson(serialNumber, 1, bookingId);
                        takeInput = false;
                    }
                } else if (option == 2) {
                    Timetable timetable = timeTableList.stream().filter(tt -> serialNumber == tt.getTimetableId()).findAny().orElse(null);
                    if (timetable != null && timetable.getSecondRemainingSeats() == 0) {
                        System.out.println("Not enough seats available\n");
                        takeInput = false;
                    } else if (timetable != null && timetable.getSecondRemainingSeats() > 0) {
                        bookLesson(serialNumber, 2, bookingId);
                        takeInput = false;
                    }
                } else if (option == 0) {
                    takeInput = false;
                } else {
                    System.out.println("Please select valid option\n");
                }
            } else {
                System.out.println("Please select valid option\n");
            }
        }
    }

    private static void lessonValidation() {

    }

    private static void viewTimetableByDay(int bookingId) {
        boolean takeInput = true;
        int option = 0;
        while (takeInput) {
            Scanner myObj = new Scanner(System.in);
            System.out.println("\n1. Saturday");
            System.out.println("2. Sunday");
            System.out.println("0. Exit");
            System.out.print("Please select: ");
            if (myObj.hasNextInt()) {
                option = myObj.nextInt();

                if (option == 1) {
                    viewTimetableByWeekend("Saturday", bookingId);
                    takeInput = false;
                } else if (option == 2) {
                    viewTimetableByWeekend("Sunday", bookingId);
                    takeInput = false;
                } else if (option == 0) {
                    takeInput = false;
                } else {
                    System.out.println("Please select valid option\n");
                }
            } else {
                System.out.println("Please select valid option\n");
            }
        }
    }

    private static void viewTimetableByWeekend(String day, int bookingId) {
        boolean takeInput = true;
        int option = 0;
        while (takeInput) {
            Scanner myObj = new Scanner(System.in);
            System.out.println("Sr.No\tWeekend\t\t1st Fitness Lesson\t\tPrice\t\t2nd Fitness Lesson\t\tPrice");
            for (Timetable tt : timeTableList) {
                if (tt.getDay().equals(day)) {
                    if (tt.getFirstExerciseType().equals("Spin")) {
                        System.out.println(tt.getTimetableId() + "\t\t" + tt.getWeekend() + "\t\t" + tt.getFirstExerciseType() + "\t\t\t\t$" + tt.getFirstPrice() + "\t\t\t" + tt.getSecondExerciseType() + "\t\t\t\t$" + tt.getSecondPrice());
                    } else {
                        System.out.println(tt.getTimetableId() + "\t\t" + tt.getWeekend() + "\t\t" + tt.getFirstExerciseType() + "\t\t\t$" + tt.getFirstPrice() + "\t\t\t" + tt.getSecondExerciseType() + "\t\t\t\t$" + tt.getSecondPrice());
                    }
                }
            }
            System.out.println("0. \t\tExit");
            System.out.print("Please select: ");
            if (myObj.hasNextInt()) {
                option = myObj.nextInt();

                if (option >= 1 && option <= 16) {
                    selectLesson(option, bookingId);
                    takeInput = false;
                } else if (option == 0) {
                    takeInput = false;
                } else {
                    System.out.println("Please select valid option\n");
                }
            } else {
                System.out.println("Please select valid option\n");
            }
        }
    }
}
