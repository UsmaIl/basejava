import com.dofler.webapp.model.Resume;

import java.util.*;

public class MainCollection {
    private static final String UUID_1;
    private static final String UUID_2;
    private static final String UUID_3;

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;

    // Используется для проверки checked-исключений при создании объекта
    static {
        UUID_1 = "uuid1";
        UUID_2 = "uuid2";
        UUID_3 = "uuid3";

        RESUME_1 = new Resume(UUID_1);
        RESUME_2 = new Resume(UUID_2);
        RESUME_3 = new Resume(UUID_3);
    }

    public static void main(String[] args) {
        Collection<Resume> resumesCollection = new ArrayList<>();
        resumesCollection.add(RESUME_1);
        resumesCollection.add(RESUME_2);
        resumesCollection.add(RESUME_3);

        for (Resume r : resumesCollection) {
            System.out.println(r);
            if (Objects.equals(r.getUuid(), UUID_1)) {
                //resumesCollection.remove(r);
            }
        }

        Iterator<Resume> iteratorResume = resumesCollection.iterator();
        while (iteratorResume.hasNext()) {
            Resume r = iteratorResume.next();
            System.out.println(r);
            if (Objects.equals(r.getUuid(), UUID_1)) {
                iteratorResume.remove();
            }
        }
        System.out.println(resumesCollection.toString());

        Map<String, Resume> mapResume = new HashMap<>();
        mapResume.put(UUID_1, RESUME_1);
        mapResume.put(UUID_2, RESUME_2);
        mapResume.put(UUID_3, RESUME_3);

        for (String uuid : mapResume.keySet()) {
            System.out.println(mapResume.get(uuid));
        }

        for (Map.Entry<String, Resume> entry : mapResume.entrySet()) {
            System.out.println(entry.getValue());
        }
    }
}
