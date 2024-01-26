package me.anjoismysign.hahaswing;

import java.util.function.Consumer;

/**
 * Represents a bubble which displays some idea to an end user.
 * Bubbles can be popped, which means that the bubble is no longer
 * displayed to the user. Bubbles can also be blown, which means
 * that the user is done with its idea and is blowing away the bubble.
 *
 * @param <T> The type of the bubble.
 */
public interface Bubble<T> {
    /**
     * Deletes the bubble from the screen.
     */
    void pop();

    /**
     * Whether the bubble supports popping.
     *
     * @return Whether the bubble supports popping.
     */
    boolean supportsPop();

    /**
     * Returns the type of the bubble.
     *
     * @return The type of the bubble.
     */
    BubbleType getType();

    /**
     * Adds a consumer to be called when the bubble is done.
     * If Bubble is an object constructor/builder, blowing it
     * means that the object is done being built.
     * If Bubble is a selector, blowing means that the user
     * has selected an option.
     *
     * @param consumer The consumer to be called.
     */
    void onBlow(Consumer<T> consumer);
}
