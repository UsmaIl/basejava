/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < storage.length; i++) {
            storage[i] = null;
        }
    }

    void save(Resume r) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = r;
                break;
            }
        }
    }

    Resume get(String uuid) {
        if (uuid == null) throw new NullPointerException();

        for (Resume r : storage) {
            if (r == null) break;

            if (uuid.equals(r.toString())) {
                return r;
            }
        }

        return null;
    }

    void delete(String uuid) {
        if (uuid == null) throw new NullPointerException();

        Resume[] tempArray = new Resume[storage.length];

        for (int i = 0, j = 0; i < storage.length && j < storage.length; i++) {

            if (uuid.equals(storage[i].toString())) {
                j++;
                //continue;
            }

            storage[i] = storage[j++];
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return new Resume[0];
    }

    int size() {
        return 0;
    }
}
