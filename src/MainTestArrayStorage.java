import com.dofler.webapp.model.Resume;
import com.dofler.webapp.storage.ArrayStorage;
import com.dofler.webapp.storage.SortedArrayStorage;
import com.dofler.webapp.storage.Storage;

/**
 * Test for your com.dofler.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    private static final Storage ARRAY_STORAGE = new ArrayStorage();
    private static final Storage SORTED_ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        final Resume r1 = new Resume("uuid1");
        final Resume r2 = new Resume("uuid2");
        final Resume r3 = new Resume("uuid3");
        final Resume r4 = new Resume("uuid4");
        final Resume r5 = new Resume("uuid5");
        final Resume r6 = new Resume("uuid6");

        //======================= Testing ARRAY_STORAGE ==============================
        System.out.println("======================= Testing ARRAY_STORAGE ==============================");
        ARRAY_STORAGE.save(r5);
        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r6);
        ARRAY_STORAGE.save(r4);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get r5: " + ARRAY_STORAGE.get(r5.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));
        System.out.println("Get uuid4: " + ARRAY_STORAGE.get("uuid4"));

        printAllArrayStorage();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAllArrayStorage();
        ARRAY_STORAGE.clear();
        printAllArrayStorage();

        System.out.println("Size: " + ARRAY_STORAGE.size());
        //======================= Testing SORTED_ARRAY_STORAGE ==============================
        System.out.println("======================= Testing SORTED_ARRAY_STORAGE ==============================");
        SORTED_ARRAY_STORAGE.save(r5);
        SORTED_ARRAY_STORAGE.save(r1);
        SORTED_ARRAY_STORAGE.save(r3);
        SORTED_ARRAY_STORAGE.save(r2);
        SORTED_ARRAY_STORAGE.save(r6);
        SORTED_ARRAY_STORAGE.save(r4);

        System.out.println("Get r1: " + SORTED_ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + SORTED_ARRAY_STORAGE.size());

        System.out.println("Get r5: " + SORTED_ARRAY_STORAGE.get(r5.getUuid()));
        System.out.println("Size: " + SORTED_ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + SORTED_ARRAY_STORAGE.get("uuid10"));
        System.out.println("Get uuid4: " + SORTED_ARRAY_STORAGE.get("uuid4"));

        printAllSortedArrayStorage();
        SORTED_ARRAY_STORAGE.delete(r1.getUuid());
        printAllSortedArrayStorage();
        SORTED_ARRAY_STORAGE.clear();
        printAllSortedArrayStorage();

        System.out.println("Size: " + SORTED_ARRAY_STORAGE.size());
    }

    static void printAllArrayStorage() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }

    static void printAllSortedArrayStorage() {
        System.out.println("\nGet All");
        for (Resume r : SORTED_ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
