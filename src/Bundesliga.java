import java.util.Arrays;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by babai-lama
 * 08.02.2017 10:14.
 */

public class Bundesliga {
    private TreeSet<TeamData> set;

    public Bundesliga() {
        this.set = new TreeSet<>();
    }

    public static String table(String[] results) {
        //Arrays.stream(results).forEach(l->{System.out.println(l);});
        return new Bundesliga().solution(results);
    }

    private String solution(String[] resuilts) {
        String pattern = "(\\d+|-):(\\d+|-)\\s*([\\w\\p{L}\\s\\.]+)(?:\\s*-\\s*)([\\w\\p{L}\\s\\.]+)";
        Arrays.stream(resuilts).forEach(l -> {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(l);
            TeamData team1;
            TeamData team2;
            while (m.find()) {
                int g1 = "-".equals(m.group(1)) ? -1 : Integer.parseInt(m.group(1));
                int g2 = "-".equals(m.group(2)) ? -1 : Integer.parseInt(m.group(2));
                team1 = new TeamData(m.group(3), g1, g2);
                team2 = new TeamData(m.group(4), g2, g1);
                set.add(team1);
                set.add(team2);
            }
        });
        final StringBuffer res = new StringBuffer();
        int count = 1;
        int c = 0;
        TeamData last = set.last();
        while (!set.isEmpty()) {
            TeamData teamData = set.pollLast();
            c++;
            if (!last.equals(teamData)) {
                count = c;
            }
            res.append(padLeft(count, 2))
                    .append(padRight(".", 2))
                    .append(teamData.toString())
            .append(set.isEmpty()?"":"\n");
            last = teamData;
        }
        return res.toString();
    }

    private class TeamData implements Comparable<TeamData> {
        private String name;
        private int playedMatches;
        private int wonMatches;
        private int tieMatches;
        private int lostMatches;
        private int shotGoals;
        private int gottenGoals;
        private int points;

        public void addMatch(int shot, int gotten) {
            if (shot >= 0 && gotten >= 0) {
                this.gottenGoals += gotten;
                this.shotGoals += shot;
                this.playedMatches++;
                if (gotten == shot) {
                    this.tieMatches++;
                    this.points++;
                }
                if (gotten < shot) {
                    this.wonMatches++;
                    this.points += 3;
                }
                if (gotten > shot) {
                    this.lostMatches++;
                }
            }
        }

        @Override
        public int compareTo(TeamData o) {
            /*
            *  The table has to be sorted by these criteria:
            *  1. Points
            *  2. If the points are the same: The difference of goals. (2:0 is better than 1:0)
            *  3. If the difference of goals is the same: More goals are better! (2:1 is better than 1:0)
            *  4. Otherwise: The teams share the same place, but ordered by the name of the team (case-insensitive!)!
            */
            if (this.getPoints() > o.getPoints()) {
                return 1;
            }
            if (this.getPoints() < o.getPoints()) {
                return -1;
            }
            if (this.getShotGoals() - this.getGottenGoals() > o.getShotGoals() - o.getGottenGoals()) {
                return 1;
            }
            if (this.getShotGoals() - this.getGottenGoals() < o.getShotGoals() - o.getGottenGoals()) {
                return -1;
            }
            if (this.getShotGoals()  > o.getShotGoals()) {
                return 1;
            }
            if (this.getShotGoals()  < o.getShotGoals()) {
                return -1;
            }
            if (this.getShotGoals() + this.getGottenGoals() > o.getShotGoals() + o.getGottenGoals()) {
                return 1;
            }
            if (this.getShotGoals() + this.getGottenGoals() > o.getShotGoals() + o.getGottenGoals()) {
                return -1;
            }

            int res = this.name.toUpperCase().compareTo(o.getName().toUpperCase());
            return -res;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer();
            sb.append(padRight(name, 30));
            sb.append(padRight(playedMatches, 3));
            sb.append(padRight(wonMatches, 3));
            sb.append(padRight(tieMatches, 3));
            sb.append(padRight(lostMatches, 3));

            sb.append(shotGoals);
            sb.append(":");
            sb.append(padRight(gottenGoals, 3));
            sb.append(points);
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TeamData)) return false;

            TeamData teamData = (TeamData) o;

            if (playedMatches != teamData.playedMatches) return false;
            if (wonMatches != teamData.wonMatches) return false;
            if (tieMatches != teamData.tieMatches) return false;
            if (lostMatches != teamData.lostMatches) return false;
            if (shotGoals != teamData.shotGoals) return false;
            if (gottenGoals != teamData.gottenGoals) return false;
            return points == teamData.points;
        }

        @Override
        public int hashCode() {
            int result = name.hashCode();
            result = 31 * result + playedMatches;
            result = 31 * result + wonMatches;
            result = 31 * result + tieMatches;
            result = 31 * result + lostMatches;
            result = 31 * result + shotGoals;
            result = 31 * result + gottenGoals;
            result = 31 * result + points;
            return result;
        }

        public TeamData() {
            this.name = "";
        }

        public TeamData(String name, int shotGoals, int gottenGoals) {
            this.name = name;
            this.addMatch(shotGoals, gottenGoals);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPlayedMatches() {
            return playedMatches;
        }

        public void setPlayedMatches(int playedMatches) {
            this.playedMatches = playedMatches;
        }

        public int getWonMatches() {
            return wonMatches;
        }

        public void setWonMatches(int wonMatches) {
            this.wonMatches = wonMatches;
        }

        public int getTieMatches() {
            return tieMatches;
        }

        public void setTieMatches(int tieMatches) {
            this.tieMatches = tieMatches;
        }

        public int getLostMatches() {
            return lostMatches;
        }

        public void setLostMatches(int lostMatches) {
            this.lostMatches = lostMatches;
        }

        public int getShotGoals() {
            return shotGoals;
        }

        public void setShotGoals(int shotGoals) {
            this.shotGoals = shotGoals;
        }

        public int getGottenGoals() {
            return gottenGoals;
        }

        public void setGottenGoals(int gottenGoals) {
            this.gottenGoals = gottenGoals;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }
    }


    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

    public static String padRight(int s, int n) {
        return padRight(String.valueOf(s), n);
    }

    public static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }

    public static String padLeft(int s, int n) {
        return padLeft(String.valueOf(s), n);
    }
}
