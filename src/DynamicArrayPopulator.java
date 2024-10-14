public class DynamicArrayPopulator {
    
    static class ArrayFiller implements Runnable {
        private int[] arr;
        private int start;
        private int end;

        public ArrayFiller(int[] arr, int start, int end) {
            this.arr = arr;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            for (int i = start; i < end; i++) {
                arr[i] = i;
            }
        }
    }
    
    public static void populateArray(int size, int numThreads) {
        int[] array = new int[size];
        Thread[] threads = new Thread[numThreads];

        int chunkSize = size / numThreads;
        long begin, end;

        begin = System.currentTimeMillis();

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int endChunk = (i == numThreads - 1) ? size : start + chunkSize;

            threads[i] = new Thread(new ArrayFiller(array, start, endChunk));
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        end = System.currentTimeMillis();
        System.out.println("Tempo di esecuzione con " + numThreads + " thread: " + (end - begin) + " ms");
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java DynamicArrayPopulator <size> <numThreads>");
            return;
        }

        try {
            int size = Integer.parseInt(args[0]);
            int numThreads = Integer.parseInt(args[1]);
    
            if (numThreads < 1 || numThreads > 8) {
                System.out.println("Threads number must be between 1 and 8");
                return;
            }

            populateArray(size, numThreads);

        } catch (NumberFormatException e) {
            System.out.println("Usage: <size> and <numThreads> must be integers");
            return;
        } catch (OutOfMemoryError e) {
            System.err.println("Java heap space out of memory. Try with a smaller size of the array");
            return;
        }
    }
}