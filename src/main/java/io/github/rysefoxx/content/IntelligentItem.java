package io.github.rysefoxx.content;

import lombok.Getter;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

/**
 * @author Rysefoxx | Rysefoxx#6772
 * @since 2/18/2022
 */
@Getter
public class IntelligentItem {

    private final ItemStack itemStack;
    private Consumer<InventoryClickEvent> consumer;
    private final IntelligentItemError error;
    private boolean canClick = true;
    private boolean canSee = true;

    @Contract(pure = true)
    public IntelligentItem(@NotNull ItemStack itemStack, @Nullable Consumer<InventoryClickEvent> eventConsumer, @Nullable IntelligentItemError error) {
        this.itemStack = itemStack;
        this.consumer = eventConsumer;
        this.error = error;
    }

    /**
     * Creates a intelligent ItemStack with a InventoryClickEvent
     *
     * @param itemStack     The ItemStack to be displayed in the inventory.
     * @param eventConsumer The InventoryClickEvent, which is performed when the player clicks on the item.
     * @param error         The error interface
     * @return An intelligent ItemStack
     */
    @Contract(pure = true)
    public static @NotNull IntelligentItem of(@NotNull ItemStack itemStack, @NotNull IntelligentItemError error, @NotNull Consumer<InventoryClickEvent> eventConsumer) {
        return new IntelligentItem(itemStack, eventConsumer, error);
    }

    /**
     * Creates a intelligent ItemStack with a InventoryClickEvent
     *
     * @param itemStack     The ItemStack to be displayed in the inventory.
     * @param eventConsumer The InventoryClickEvent, which is performed when the player clicks on the item.
     * @return An intelligent ItemStack
     */
    @Contract(pure = true)
    public static @NotNull IntelligentItem of(@NotNull ItemStack itemStack, @NotNull Consumer<InventoryClickEvent> eventConsumer) {
        return new IntelligentItem(itemStack, eventConsumer, null);
    }

    /**
     * Creates a intelligent ItemStack without a InventoryClickEvent
     *
     * @param itemStack The itemStack to be displayed in the inventory.
     * @return An intelligent ItemStack
     */
    @Contract(pure = true)
    public static @NotNull IntelligentItem empty(@NotNull ItemStack itemStack) {
        return new IntelligentItem(itemStack, event -> {
        }, null);
    }

    /**
     * Creates a intelligent ItemStack without a InventoryClickEvent
     *
     * @param itemStack The itemStack to be displayed in the inventory.
     * @param error     The error interface
     * @return An intelligent ItemStack
     */
    @Contract(pure = true)
    public static @NotNull IntelligentItem empty(@NotNull ItemStack itemStack, @NotNull IntelligentItemError error) {
        return new IntelligentItem(itemStack, event -> {
        }, error);
    }

    /**
     * Creates an Intelligent ItemStack where the player can take the item from the inventory.
     *
     * @param itemStack The item that can be removed.
     * @return An intelligent ItemStack
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull IntelligentItem ignored(@NotNull ItemStack itemStack) {
        return new IntelligentItem(itemStack, null, null);
    }

    /**
     * Creates an Intelligent ItemStack where the player can take the item from the inventory.
     *
     * @param itemStack The item that can be removed.
     * @param error     The error interface
     * @return An intelligent ItemStack
     */
    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull IntelligentItem ignored(@NotNull ItemStack itemStack, @NotNull IntelligentItemError error) {
        return new IntelligentItem(itemStack, null, error);
    }

    /**
     * Removes the consumer from an IntelligentItem
     */
    public void clearConsumer() {
        this.consumer = event -> {
        };
    }

    public IntelligentItem canClick(@NotNull BooleanSupplier supplier) {
        this.canClick = supplier.getAsBoolean();
        return this;
    }

    public IntelligentItem canSee(@NotNull BooleanSupplier supplier) {
        this.canSee = supplier.getAsBoolean();
        return this;
    }


    /**
     * Changes the ItemStack of an existing ItemStack without changing the consumer.
     *
     * @param newItemStack The new ItemStack
     * @return The new intelligent ItemStack
     */
    public @NotNull IntelligentItem update(@NotNull ItemStack newItemStack) {
        return new IntelligentItem(newItemStack, this.consumer, this.error);
    }

    /**
     * Changes the ItemStack of an existing Intelligent with changing the consumer.
     *
     * @param newIntelligentItem The new IntelligentItem
     * @return The new intelligent ItemStack
     */
    public @NotNull IntelligentItem update(@NotNull IntelligentItem newIntelligentItem) {
        return new IntelligentItem(newIntelligentItem.getItemStack(), newIntelligentItem.getConsumer(), this.error);
    }
}
