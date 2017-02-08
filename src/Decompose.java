import java.util.StringJoiner;
import java.util.TreeSet;

public class Decompose {

    private TreeSet<Result> treeSet;
    Long n;
    int error;

    public Decompose() {
        error = 0;
    }

    public String decompose(long n) {
        this.treeSet = new TreeSet<>();
        this.n = (Long) n;
        StringJoiner joiner = new StringJoiner(" ");
        Result result;
        while (true) {
            if (this.treeSet.isEmpty()) {
                Long newN = n - 1;
                result = new Result(newN, n * n - newN * newN, 0L, 0L);
            } else {
                result = this.treeSet.pollFirst();
            }
            if (calcSqrt(result)) {
                break;
            } else {
                repairSet();
            }
        }
        treeSet.stream().forEach(l -> joiner.add(l.getN().toString()));
        System.out.println();
        return joiner.toString();
    }

    private void repairSet() {
        if (this.treeSet.isEmpty()) {
            this.error++;
        }
        Result result = this.treeSet.pollFirst();
        if ((result.getError() > 0) && (result.getDelta() < 5)) {
            result = this.treeSet.pollFirst();
            //result.setDelta();
        }
    }

    private boolean calcSqrt(Result result) {
        long n = result.getN();
        long delta = result.getDelta();
        long prevDelta = result.getPrevDelta();
        long error = result.getError();
        boolean res = false;
        /*//first iteration
        if (this.treeSet.isEmpty() && (n * n == this.n * this.n)) {
            Long newN = n - 1;
            result = new Result(newN, n * n - newN * newN, 0L, 0L);
        }*/

        if (!this.treeSet.add(new Result(n, delta, prevDelta, error))) return res;
        if (delta == 0) return true;
        System.out.println(n + " " + delta + " " + prevDelta + " " + error);
        Long sq = (long) Math.sqrt(delta);
        result.setN((long) Math.sqrt(delta));
        result.setPrevDelta((long) Math.sqrt(delta));
        result.setDelta(delta - sq * sq);
        result.setError(error);
        return calcSqrt(result);
    }

    class Result implements Comparable<Result> {
        private Long n;
        private Long delta;
        private Long error;
        private Long prevDelta;

        public Result(Long n, Long delta, Long prevDelta, Long error) {
            this.n = n;
            this.delta = delta;
            this.error = error;
            this.prevDelta = prevDelta;
        }

        public Result() {
            new Result(0L, 0L, 0L, 0L);
        }

        public Long getN() {
            return n;
        }

        public void setN(Long n) {
            this.n = n;
        }

        public Long getDelta() {
            return delta;
        }

        public void setDelta(Long delta) {
            this.delta = delta;
        }

        public Long getError() {
            return error;
        }

        public void setError(Long error) {
            this.error = error;
        }

        public Long getPrevDelta() {
            return prevDelta;
        }

        public void setPrevDelta(Long prevDelta) {
            this.prevDelta = prevDelta;
        }

        @Override
        public int compareTo(Result o) {
            if (this.n > o.n) return 1;
            if (this.n < o.n) return -1;
            return 0;
        }
    }
}

/*
10 -- 6 8
14 -- 4 6 12
12 -- 1 2 [3 7 9]
625 -- 2 [5 8 34] 624
7100 -- [2 3 5] 119 7099
12345 -- [12345] 2 6 157 12344
1234567 -- [2 8 32] 1571 1234566
 */