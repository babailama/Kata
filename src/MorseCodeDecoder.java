
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by babai-lama
 * 13.01.2017 9:48.
 */

public class MorseCodeDecoder {
    private List<MorseCluster> clusters;
    private TreeMap<Integer, MorseElement> elements;

    public static String decodeBitsAdvanced(String bits) {
        //System.out.println("bits='" + bits + "'");
        if (bits != null && !bits.isEmpty()) {
            String morse = bits.replaceAll("^0+|0+$", "");
            String retStr = new MorseCodeDecoder().myDecodeBitsAdvanced(morse);
            //System.out.println("retStr='" + retStr + "'");
            return retStr;
        } else return null;
    }

    public MorseCodeDecoder() {
        clusters = new ArrayList<>();
        elements = new TreeMap<>();
    }

    public String myDecodeBitsAdvanced(String bits) {
        StringBuffer sb = new StringBuffer();
        boolean finish = false;
        initElements(bits);
        initCentroids();
        int iterations = 0;
        while (!finish) {
            clearClusters();
            List<MorseElement> centroids = getCentroids();
            assignCluster();
            calculateCentroids();
            List<MorseElement> currentCentroids = getCentroids();
            int distance = 0;
            for (int i = 0; i < centroids.size(); i++) {
                distance += centroids.get(i).myDistance(currentCentroids.get(i));
            }
            if (distance == 0) {
                finish = true;
            }
            iterations++;
            if (iterations % 10000 == 0) System.out.print(".");
        }
        for (Map.Entry<Integer, MorseElement> element : this.elements.entrySet()) {
            switch (element.getValue().getClusterId()) {
                case 0:
                    if (element.getValue().getElementValue() == 1) {
                        sb.append('.');
                        element.getValue().setMeanChar('.');
                    }
                    break;
                case 1:
                    if (element.getValue().getElementValue() == 1) {
                        sb.append('-');
                        element.getValue().setMeanChar('-');
                    } else {
                        sb.append(' ');
                        element.getValue().setMeanChar('_');
                    }
                    break;
                case 2:
                    if (element.getValue().getElementValue() == 1) {
                        sb.append('-');
                        element.getValue().setMeanChar('-');
                    } else {
                        sb.append("   ");
                        element.getValue().setMeanChar('~');
                    }
                    break;
            }
            /*StringBuffer testSb = new StringBuffer();
            testSb.append(element.getValue().elementValue)
                    .append("x")
                    .append(element.getValue().elementLength)
                    .append(" ")
                    .append(element.getValue().getMeanChar())
                    .append(" clId ")
                    .append(element.getValue().getClusterId());
            System.out.println(testSb.toString());*/
        }
        printDistribution();
        return sb.toString();
    }

    private void printDistribution() {
        List<DistributionUnit> el = new ArrayList<>();
        for (Map.Entry<Integer, MorseElement> element : this.elements.entrySet()) {
            double len = element.getValue().getElementLength();
            int val = element.getValue().getElementValue();
            DistributionUnit unit;
            if (el.size() > 0) {
                boolean flag = true;
                for (int i = 0; i < el.size(); i++) {
                    unit = el.get(i);
                    if (unit.length == (int) len && unit.value == val) {
                        el.get(i).count++;
                        flag = false;
                    }
                }
                if (flag) el.add(new DistributionUnit((int) len, val, 1));
            } else {
                el.add(new DistributionUnit((int) len, val, 1));
            }

        }
        Collections.sort(el);
        el.stream().forEach(d->{
            System.out.println(new StringBuffer().append(d.value)
            .append(" ")
            .append(d.length)
            .append(" ")
            .append(new String(new char[d.count]).replace('\0', '*'))
            .toString());
        });
        System.out.print("");
    }

    private void initElements(String bits) {
        int n = 0;
        MorseElement element = new MorseElement(0, 1);
        for (int i = 0, m = bits.length(); i < m; i++) {
            char c = bits.charAt(i);
            int val = c == '0' ? 0 : 1;
            if (element.getElementValue() != val) {
                elements.put(n++, element);
                element = new MorseElement(1, val);
            } else {
                element.setElementLength(element.getElementLength() + 1);
            }
        }
        elements.put(n++, element);
    }

    private void initCentroids() {
        clusters = new ArrayList<>();
        TreeSet<Double> treeSet = getUniqLength();
        double middle = 0;
        for (double uniq : treeSet) {
            middle += uniq;
        }
        middle = (middle / treeSet.size());
        double max = treeSet.last();
        double min = treeSet.first();
        for (int i = 0; i < 3; i++) {
            clusters.add(new MorseCluster(i));
        }
        clusters.get(0).setCentroid(new MorseElement(min, -1, 0));
        clusters.get(1).setCentroid(new MorseElement(middle, -1, 1));
        clusters.get(2).setCentroid(new MorseElement(max, -1, 2));
    }

    private void clearClusters() {
        for (MorseCluster cluster : clusters) {
            cluster.clear();
        }
    }

    private void assignCluster() {
        double max = Double.MAX_VALUE;
        double min;
        int cluster = 0;
        double distance;

        for (Map.Entry<Integer, MorseElement> element : this.elements.entrySet()) {
            min = max;
            for (int i = 0; i < 3; i++) {
                MorseCluster c = clusters.get(i);
                distance = element.getValue().myDistance(c.getCentroid());
                if (distance < min) {
                    min = distance;
                    cluster = i;
                }
            }
            //System.out.println(element.getValue().getElementValue() + " " +element.getValue().getElementLength() + " " + distance);
            element.getValue().setClusterId(cluster);
            clusters.get(cluster).addMorseElement(element.getValue());
        }
    }

    private void calculateCentroids() {
        for (MorseCluster cluster : clusters) {
            double length = 0;
            List<MorseElement> elements = cluster.getList();
            int n_elements = elements.size();
            for (MorseElement m : elements) {
                length += m.getElementLength();
            }
            MorseElement centroid = cluster.getCentroid();
            if (n_elements > 0) {
                double newLength = length / n_elements;
                centroid.setElementLength(newLength);
            }
        }
    }

    private List<MorseElement> getCentroids() {
        List<MorseElement> list = new ArrayList<>();
        for (MorseCluster cluster : clusters) {
            MorseElement element = cluster.getCentroid();
            MorseElement clone = new MorseElement(element.getElementLength(), element.getElementValue(), cluster.getId());
            list.add(clone);
        }
        return list;
    }

    private TreeSet<Double> getUniqLength() {
        TreeSet<Double> treeSet = new TreeSet<>();
        for (Map.Entry<Integer, MorseElement> element : this.elements.entrySet()) {
            treeSet.add(element.getValue().getElementLength());
        }
        return treeSet;
    }


    public static String decodeMorse(String morseCode) {

        if (morseCode != null && !morseCode.isEmpty()) {
            System.out.println("morse-code " + morseCode);
            // your brilliant code here, remember that you can access the preloaded Morse code table through MorseCode.get(code)
            String morse = morseCode;
            StringBuffer sb = new StringBuffer();
            String regexp = "^(\\s+)";
            Pattern pattern = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(morse);
            if (matcher.find()) {
                morse = matcher.replaceFirst("");
            }
            regexp = "(\\s+)$";
            pattern = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(morse);
            if (matcher.find()) {
                morse = matcher.replaceAll("");
            }
            regexp = "\\s{3}";
            pattern = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
            String[] words = pattern.split(morse);
            Arrays.asList(words).forEach(word -> {
                //StringBuffer tr = new StringBuffer();
                //Arrays.asList(((Pattern.compile("(\\s)")).split(word))).forEach(ch -> tr.append(MorseCode.get(ch)));
                //System.out.println(word + " " + tr.toString());
                Arrays.asList(((Pattern.compile("(\\s)")).split(word))).forEach(ch -> sb.append(MorseCode.get(ch)));
                sb.append(" ");
            });
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        } else return null;
    }


    class MorseElement implements Comparable<MorseElement> {
        private double elementLength;
        private int elementValue;
        private int clusterId;
        private char meanChar;

        public char getMeanChar() {
            return meanChar;
        }

        public void setMeanChar(char meanChar) {
            this.meanChar = meanChar;
        }

        protected double myDistance(MorseElement centroid) {
            return Math.abs((this.getElementLength() - (centroid.getElementLength()))) / 11;
            //return  Math.sqrt()
        }

        public double getElementLength() {
            return elementLength;
        }

        public void setElementLength(double elementLength) {
            this.elementLength = elementLength;
        }

        public int getElementValue() {
            return elementValue;
        }

        public void setElementValue(int elementValue) {
            this.elementValue = elementValue;
        }

        public int getClusterId() {
            return clusterId;
        }

        public void setClusterId(int clusterId) {
            this.clusterId = clusterId;
        }

        public MorseElement(double elementLength, int elementValue, int clusterId) {
            this.elementLength = elementLength;
            this.elementValue = elementValue;
            this.clusterId = clusterId;
        }

        public MorseElement(double elementLength, int elementValue) {
            this.elementLength = elementLength;
            this.elementValue = elementValue;
            this.clusterId = 0;
        }

        @Override
        public int compareTo(MorseElement o) {
            if (this.elementLength < o.elementLength) {
                return -1;
            }
            if (this.elementLength > o.elementLength) {
                return 1;
            }
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MorseElement)) return false;

            MorseElement that = (MorseElement) o;

            if (elementLength != that.elementLength) return false;
            return elementValue == that.elementValue;
        }

        @Override
        public int hashCode() {
            int result = (int) elementLength;
            result = 31 * result + elementValue;
            return result;
        }

        @Override
        public String toString() {
            return new StringBuffer()
                    .append(" elementLength ").append(elementLength)
                    .append(" elementValue ").append(elementValue)
                    .append(" clusterId ").append(clusterId)
                    .toString();
        }
    }

    class MorseCluster {
        private List<MorseElement> list;
        private MorseElement centroid;
        private int id;

        public List<MorseElement> getList() {
            return list;
        }

        public void setList(List<MorseElement> list) {
            this.list = list;
        }

        public MorseElement getCentroid() {
            return centroid;
        }

        public void setCentroid(MorseElement centroid) {
            this.centroid = centroid;
        }

        public int getId() {
            return id;
        }

        public MorseCluster(int id) {
            this.list = new ArrayList<>();
            this.centroid = null;
            this.id = id;
        }

        public void addMorseElement(MorseElement element) {
            this.list.add(element);
        }

        public void setMorseElements(List list) {
            this.setList(list);
        }

        public void clear() {
            this.list.clear();
        }
    }

    class DistributionUnit implements Comparable<DistributionUnit>, Comparator<DistributionUnit> {
        public int length;
        public int count;
        public int value;

        public DistributionUnit(int length, int value, int count) {
            this.length = length;
            this.count = count;
            this.value = value;
        }

        @Override
        public int compareTo(DistributionUnit o) {
            if (length > o.length) {
                return 1;
            }
            if (length < o.length) {
                return -1;
            }
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof DistributionUnit)) return false;

            DistributionUnit unit = (DistributionUnit) o;

            return length == unit.length;
        }

        @Override
        public int hashCode() {
            return length;
        }

        @Override
        public int compare(DistributionUnit o1, DistributionUnit o2) {
            if (o1.length == o2.length) {
                return o1.count - o2.count;
            }
            return 0;
        }
    }
}
