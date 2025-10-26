package com.blablabla.util;

public class PerformanceTracker {
    private long startTime;
    private long endTime;
    private int comparisonCount;
    private int unionOperations;
    private int findOperations;
    private int edgeProcessingCount;

    public void startTimer() {
        startTime = System.nanoTime();
        resetCounters();
    }

    public void stopTimer() { endTime = System.nanoTime(); }

    public double getElapsedTimeMillis() {
        return (endTime - startTime) / 1_000_000.0;
    }

    public void incrementComparisons(int count) { comparisonCount += count; }
    public void incrementUnionOperations() { unionOperations++; }
    public void incrementFindOperations() { findOperations++; }
    public void incrementEdgeProcessing() { edgeProcessingCount++; }

    public int getTotalOperations() {
        return comparisonCount + unionOperations + findOperations + edgeProcessingCount;
    }

    private void resetCounters() {
        comparisonCount = unionOperations = findOperations = edgeProcessingCount = 0;
    }

    public int getComparisonCount() { return comparisonCount; }
    public int getUnionOperations() { return unionOperations; }
    public int getFindOperations() { return findOperations; }
    public int getEdgeProcessingCount() { return edgeProcessingCount; }
}