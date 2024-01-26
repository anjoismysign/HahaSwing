package me.anjoismysign.hahaswing;

import me.anjoismysign.anjo.swing.AnjoPane;
import me.anjoismysign.anjo.swing.OptionType;
import me.anjoismysign.anjo.swing.components.AnjoComboBox;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
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
     * Displays a selector bubble to the user.
     *
     * @param collection The collection of objects to be selected.
     * @param <T>        The type of the objects in the collection.
     * @return A bubble which can be blown to get the selected object.
     */
    @NotNull
    public <T> Bubble<T> selector(Collection<T> collection) {
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
    public <T> Bubble<T> selector(Collection<T> collection,
                                  @Nullable String title,
                                  @Nullable String comboBoxTitle) {
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
