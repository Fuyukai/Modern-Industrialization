/*
 * MIT License
 *
 * Copyright (c) 2020 Azercoco & Technici4n
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package aztech.modern_industrialization.machinesv2.blockentities;

import aztech.modern_industrialization.inventory.ConfigurableItemStack;
import aztech.modern_industrialization.inventory.MIInventory;
import aztech.modern_industrialization.inventory.SlotPositions;
import aztech.modern_industrialization.machinesv2.MachineBlockEntity;
import aztech.modern_industrialization.machinesv2.components.OrientationComponent;
import aztech.modern_industrialization.machinesv2.gui.MachineGuiParameters;
import aztech.modern_industrialization.machinesv2.helper.OrientationHelper;
import aztech.modern_industrialization.machinesv2.models.MachineCasings;
import aztech.modern_industrialization.machinesv2.models.MachineModelClientData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;

public class ConfigurableChestMachineBlockEntity extends MachineBlockEntity implements Tickable {

    private final OrientationComponent orientation;
    private final MIInventory inventory;

    public ConfigurableChestMachineBlockEntity(BlockEntityType<?> type) {
        super(type, new MachineGuiParameters.Builder("configurable_chest", true).build());
        orientation = new OrientationComponent(new OrientationComponent.Params(true, false, false));

        List<ConfigurableItemStack> stacks = new ArrayList<>();
        for (int i = 0; i < 21; i++) {
            stacks.add(ConfigurableItemStack.standardIOSlot(true));
        }
        SlotPositions itemPositions = new SlotPositions.Builder().addSlots(16, 16, 3, 7).build();
        inventory = new MIInventory(stacks, Collections.EMPTY_LIST, itemPositions, SlotPositions.empty());
        this.registerComponents(orientation, inventory);
    }

    @Override
    public MIInventory getInventory() {
        return inventory;
    }

    @Override
    protected ActionResult onUse(PlayerEntity player, Hand hand, Direction face) {
        return OrientationHelper.onUse(player, hand, face, orientation, this);
    }

    @Override
    protected MachineModelClientData getModelData() {
        MachineModelClientData data = new MachineModelClientData(MachineCasings.STEEL_CRATE);
        orientation.writeModelData(data);
        return data;
    }

    @Override
    public void onPlaced(LivingEntity placer, ItemStack itemStack) {
        orientation.onPlaced(placer, itemStack);
    }

    @Override
    public void tick() {
        // TODO Add auto output
    }
}
