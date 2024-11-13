class Feedback {
    private static int[] ratings = new int[100];   // array holding ratings
    private static int ratingIndex = 0;            // 0-indexing of above ratings array


    // Asking for Feedback from User
    public static void getFeedback(Scanner sc) {
        int rating=0;
        while (true) {
            System.out.print("Please rate your ATM experience (1 to 5): ");
            try{
                rating = sc.nextInt();
            }
            catch(Exception e){
                System.out.println("Please enter a number between 1 and 5.");
                sc.next();
                continue;
            }
            if (rating >= 1 && rating <= 5) {
                ratings[ratingIndex++] = rating;
                System.out.println("Thank you for your feedback!");
                break;
            } else {
                System.out.println("Invalid rating. Please enter a number between 1 and 5.");
            }
        }
        System.out.println("You rated the service: " + rating + " stars.");   
    }
}
