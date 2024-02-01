package me.anjoismysign.hahaswing;

import me.anjoismysign.anjo.swing.AnjoComponent;
import me.anjoismysign.anjo.swing.AnjoPane;
import me.anjoismysign.anjo.swing.OptionType;
import me.anjoismysign.anjo.swing.components.AnjoComboBox;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class BubbleFactory {
    private static BubbleFactory instance;

    public static BubbleFactory getInstance() {
        if (instance == null) {
            instance = new BubbleFactory();
        }
        return instance;
    }

    /**
     * Displays a selector bubble to the user.
     *
     * @param collection The collection of objects to be selected.
     * @param <T>        The type of the objects in the collection.
     * @return A bubble which can be blown to get the selected object.
     */
    @NotNull
    public <T extends Displayable> Bubble<T> displayableSelector(Collection<T> collection) {
        return displayableSelector(collection, null, null);
    }

    /**
     * Displays a selector bubble to the user.
     *
     * @param collection    The collection of objects to be selected.
     * @param title         The title of the bubble.
     * @param comboBoxTitle The title of the combo box.
     * @param <T>           The type of the objects in the collection.
     * @return A bubble which can be blown to get the selected object.
     */
    @NotNull
    public <T extends Displayable> Bubble<T> displayableSelector(Collection<T> collection,
                                                                 @Nullable String title,
                                                                 @Nullable String comboBoxTitle) {
        String className = collection.iterator().next().getClass().getSimpleName();
        Map<String, T> references = new HashMap<>();
        collection.forEach(t -> references.put(t.display(), t));
        title = title == null ? className + " selector" : title;
        comboBoxTitle = comboBoxTitle == null ? className : comboBoxTitle;
        AnjoPane anjoPane = AnjoPane.build(title,
                OptionType.OK_CANCEL,
                AnjoComboBox.build(comboBoxTitle, references.keySet()));
        return new Bubble<T>() {
            public void pop() {
                throw new UnsupportedOperationException("Not implemented yet");
            }

            public boolean supportsPop() {
                return false;
            }

            public BubbleType getType() {
                return BubbleType.SELECTOR;
            }

            public void onBlow(Consumer<T> consumer) {
                if (!anjoPane.saidYes())
                    return;
                String key = anjoPane.getComboBoxText(0);
                T value = references.get(key);
                consumer.accept(value);
            }
        };
    }

    /**
     * Displays a controller bubble to the user.
     *
     * @param title      The title of the bubble.
     * @param components The components of the bubble.
     * @return A bubble which can be blown to get the selected object.
     */
    public Bubble<AnjoPane> controller(@NotNull String title,
                                       @NotNull AnjoComponent... components) {
        return controller(null, title, null, null, components);
    }

    /**
     * Displays a controller bubble to the user.
     *
     * @param onPop      The consumer to be called when the bubble is popped.
     * @param title      The title of the bubble.
     * @param image      The image of the bubble.
     * @param components The components of the bubble.
     * @return A bubble which can be blown to get the selected object.
     */
    public Bubble<AnjoPane> controller(@Nullable Consumer<AnjoPane> onPop,
                                       @NotNull String title,
                                       @Nullable Image image,
                                       @Nullable Consumer<File> onDrop,
                                       @NotNull AnjoComponent... components) {
        Objects.requireNonNull(title, "'title' cannot be null");
        Objects.requireNonNull(components, "'components' cannot be null");
        AnjoPane anjoPane = AnjoPane.build(title, OptionType.OK_CANCEL, image, onDrop, components);
        Bubble<AnjoPane> bubble = new Bubble<>() {
            public void pop() {
                if (onPop != null)
                    onPop.accept(anjoPane);
            }

            public boolean supportsPop() {
                return onPop != null;
            }

            public BubbleType getType() {
                return BubbleType.CONTROLLER;
            }

            public void onBlow(Consumer<AnjoPane> consumer) {
                if (!anjoPane.saidYes())
                    return;
                consumer.accept(anjoPane);
            }
        };
        if (anjoPane.didCancel())
            bubble.pop();
        return bubble;
    }

    /**
     * Displays a selector bubble to the user.
     *
     * @param collection The collection of objects to be selected.
     * @param <T>        The type of the objects in the collection.
     * @return A bubble which can be blown to get the selected object.
     */
    @NotNull
    public <T> Bubble<T> selector(@NotNull Collection<T> collection) {
        return selector(collection, null, null);
    }

    /**
     * Displays a selector bubble to the user.
     *
     * @param collection    The collection of objects to be selected.
     * @param title         The title of the bubble.
     * @param comboBoxTitle The title of the combo box.
     * @param <T>           The type of the objects in the collection.
     * @return A bubble which can be blown to get the selected object.
     */
    @NotNull
    public <T> Bubble<T> selector(@NotNull Collection<T> collection,
                                  @Nullable String title,
                                  @Nullable String comboBoxTitle) {
        Objects.requireNonNull(collection, "Collection cannot be null");
        String className = collection.iterator().next().getClass().getSimpleName();
        Map<String, T> references = new HashMap<>();
        collection.forEach(t -> references.put(t.toString(), t));
        title = title == null ? className + " selector" : title;
        comboBoxTitle = comboBoxTitle == null ? className : comboBoxTitle;
        AnjoPane anjoPane = AnjoPane.build(title,
                OptionType.OK_CANCEL,
                AnjoComboBox.build(comboBoxTitle, references.keySet()));
        return new Bubble<T>() {
            public void pop() {
                throw new UnsupportedOperationException("Not implemented yet");
            }

            public boolean supportsPop() {
                return false;
            }

            public BubbleType getType() {
                return BubbleType.SELECTOR;
            }

            public void onBlow(Consumer<T> consumer) {
                if (!anjoPane.saidYes())
                    return;
                String key = anjoPane.getComboBoxText(0);
                T value = references.get(key);
                consumer.accept(value);
            }
        };
    }
}
