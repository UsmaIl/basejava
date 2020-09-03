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

        for (int i = 0, j = 0; j < storage.length; i++, j++) {
            if (storage[i] == null) break;

            if (uuid.equals(storage[i].toString())) {
                j++;
            }
            storage[i] = storage[j];
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        int iterator = 0;

        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                break;
            }
            iterator++;
        }

        Resume[] tempArray = new Resume[iterator];

        for (int i = 0; i < tempArray.length; i++) {
            tempArray[i] = storage[i];
        }

        return tempArray;
    }

    int size() {
        int iterator = 0;

        for (Resume r : storage) {
            if (r == null) {
                break;
            }
            iterator++;
        }

        return iterator;
    }
}
