package com.pony.validation;

/**
 * ArbVentures 2014.
 * User: martin
 * Date: 9/11/14
 * Time: 10:12 AM
 */
public abstract class ValidationScore {
    private double score;

    protected ValidationScore(double score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ValidationScore that = (ValidationScore) o;
        return Double.compare(that.score, score) == 0;
    }

    @Override
    public int hashCode() {
        long temp = score != +0.0d ? Double.doubleToLongBits(score) : 0L;
        return (int) (temp ^ (temp >>> 32));
    }
}
