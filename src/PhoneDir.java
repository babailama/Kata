import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by babai-lama
 * 06.02.2017 16:18.
 */
public class PhoneDir {
    public static String phone(String strng, String num) {
        PhoneDir phoneDir = new PhoneDir();
        return phoneDir.ph(strng, num);
    }

    private String ph(String strng, String num) {
        String res = "";
        List<PhoneBookRecord> phoneBook = new ArrayList();
        String phonePatternRegex = "(\\+[0-9]{1,2}-[0-9]{3}-[0-9]{3}-[0-9]{4})";
        String namePatternRegex = "(<(.+?)>)";
        String trashPatternRegex = "[<>\\+!$,\\*?/:;]";
        Pattern phonePattern = Pattern.compile(phonePatternRegex);
        Pattern namePattern = Pattern.compile(namePatternRegex);
        Arrays.stream(strng.split("\\r?\\n")).forEach(str ->
        {
            String line = str;
            String phone = "";
            Matcher m = phonePattern.matcher(line);
            if (m.find()) {
                phone = m.group().replace("+", "");
                line = line.replace(phone, "");
            }
            m = namePattern.matcher(line);
            String name = "";
            if (m.find()) {
                name = m.group().replace("<", "").replace(">", "");
                line = line.replaceAll(name, "");
            }
            System.out.println(line.replaceAll("\\s+","#"));
            Pattern trashPattern = Pattern.compile(trashPatternRegex);
            m = trashPattern.matcher(line);
            line = m.replaceAll(" ").trim();
            Pattern space = Pattern.compile("\\s{2,}");
            m = space.matcher(line);
            line = m.replaceAll(" ");
            phoneBook.add(new PhoneBookRecord(phone, name, line));
            System.out.println(phone+"|"+name+"|"+line);
        });
        PhoneBookRecord search = new PhoneBookRecord(num, "", "");
        int index = phoneBook.indexOf(search);
        if (index < 0) {
            res = "Error => Not found: " + num;
        } else {
            if (index != phoneBook.lastIndexOf(search)) {
                res = "Error => Too many people: " + num;
            } else{
                res = "Phone => "+phoneBook.get(index).getPhoneNumber()+
                        ", Name => "+phoneBook.get(index).getName()+
                        ", Address => "+phoneBook.get(index).getAddress();
            }
        }
        return res;
    }

    class PhoneBookRecord {
        private String phoneNumber;
        private String name;
        private String address;

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public PhoneBookRecord(String phoneNumber, String name, String address) {
            this.phoneNumber = phoneNumber;
            this.name = name;
            this.address = address;
        }

        public PhoneBookRecord() {
            new PhoneBookRecord("", "", "");
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PhoneBookRecord)) return false;

            PhoneBookRecord that = (PhoneBookRecord) o;

            return phoneNumber != null ? phoneNumber.equals(that.phoneNumber) : that.phoneNumber == null;
        }

        @Override
        public int hashCode() {
            return phoneNumber != null ? phoneNumber.hashCode() : 0;
        }
    }
}
