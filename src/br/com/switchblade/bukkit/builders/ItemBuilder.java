package br.com.switchblade.bukkit.builders;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {

	private ItemStack itemstack;

	public ItemBuilder(ItemStack item) {
		this.itemstack = item;
	}

	public ItemBuilder(Material material) {
		this(new ItemStack(material));
	}

	public ItemBuilder(Material material, int amount) {
		this(new ItemStack(material, amount));
	}

	public ItemBuilder(Material material, int amount, short data) {
		this(new ItemStack(material, amount, data));
	}

	public ItemStack create() {
		return itemstack;
	}

	public ItemBuilder applyMeta(Consumer<ItemMeta> meta) {
		ItemMeta itemMeta = this.itemstack.getItemMeta();

		meta.accept(itemMeta);
		this.itemstack.setItemMeta(itemMeta);
		return this;
	}

	public ItemBuilder applyStack(Consumer<ItemStack> stack) {
		stack.accept(itemstack);

		return this;
	}

	public ItemBuilder setName(String name) {
		return applyMeta(meta -> meta.setDisplayName(name));
	}

	public ItemBuilder setLore(List<String> lore) {
		return applyMeta(meta -> meta.setLore(lore));
	}

	public ItemBuilder setEnchant(Enchantment ench, int level) {
		return applyStack(stack -> stack.addUnsafeEnchantment(ench, level));
	}

	public ItemBuilder setEnchants(List<Enchantment> enchs, int defaultLevel) {
		return applyStack(stack -> enchs.forEach(ench -> stack.addEnchantment(ench, defaultLevel)));
	}

	public ItemBuilder setEnchants(Map<Enchantment, Integer> enchs) {
		return applyStack(stack -> stack.addEnchantments(enchs));
	}

}