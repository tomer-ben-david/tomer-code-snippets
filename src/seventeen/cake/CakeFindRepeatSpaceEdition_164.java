package seventeen.cake;

// Cake #164 https://www.interviewcake.com/question/java/find-duplicate-optimize-for-space-beast-mode
public class CakeFindRepeatSpaceEdition_164 {

    public static class FindTheDuplicate {
        public static int findDup(int[] arr) {
            int lowerVal = 1;
            int upperVal = arr.length;


            int midVal = upperVal / 2;
            int countInLowRange = 0;
            int countInUpperRange = 0;

            while (midVal - lowerVal > 0 && (upperVal - midVal + 1) > 0) {

                countInLowRange = 0;
                countInUpperRange = 0;
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i] <= midVal && arr[i] >= lowerVal) {
                        countInLowRange++;
                    } else if (arr[i] > midVal && arr[i] <= upperVal) {
                        countInUpperRange++;
                    }
                }

                if (countInLowRange > (midVal - lowerVal + 1)) {
                    upperVal = midVal;
                } else {
                    lowerVal = midVal + 1;
                }
                midVal = (upperVal + lowerVal) / 2;
            }

            return midVal;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int arr[] = new int[] { 1, 3, 2, 4, 5, 6, 7, 8, 9, 1, 10 };
        System.out.println(FindTheDuplicate.findDup(arr));

    }




}