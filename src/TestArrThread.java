public class TestArrThread {

    public static void main(String[] args) {
        int SIZE = 5000000;
        int arr1[] = new int[SIZE];
        int arr2[] = new int[SIZE];
        int finalArr[] = new int[SIZE * 2];

        long begin, end;

        
        begin = System.currentTimeMillis();
        Thread t1 = new Thread( () -> {
            for(int i = 0; i < SIZE; i++) {
                arr1[i] = i;
            }
        });

        Thread t2 = new Thread( () -> {
            for(int i = 0; i < SIZE; i++) {
                arr2[i] = i + SIZE;
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(arr1, 0, finalArr, 0, SIZE); // Copia arr1 in finalArr
        System.arraycopy(arr2, 0, finalArr, SIZE, SIZE); // Copia arr2 in finalArr, partendo da SIZE

        end = System.currentTimeMillis();
        
        System.out.println("Tempo di esecuzione: " + (end - begin) + " ms");
        // Verifica della dimensione finale
        System.out.println("Dimensione finalArr: " + finalArr.length);
    }
}
