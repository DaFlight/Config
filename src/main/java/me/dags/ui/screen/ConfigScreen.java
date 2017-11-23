package me.dags.ui.screen;

import com.google.common.base.Preconditions;
import me.dags.ui.UIMapper;
import me.dags.ui.platform.Keys;
import me.dags.ui.platform.Platform;
import me.dags.ui.platform.RenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author dags <dags@dags.me>
 */
public class ConfigScreen<T> extends GuiScreen implements RenderContext {

    static {
        Platform.setInput(new LWJGLInput());
    }

    private final File source;
    private final Supplier<T> supplier;
    private final UIMapper<T> uiMapper;

    private T instance;

    private ConfigScreen(Class<T> type) {
        this(null, type, null);
    }

    private ConfigScreen(File source, Class<T> type, Supplier<T> supplier) {
        super();
        super.mc = Minecraft.getMinecraft();
        super.fontRenderer = mc.fontRenderer;

        this.source = source;
        this.supplier = supplier;
        this.uiMapper = UIMapper.create(type);

        if (source != null && supplier != null) {
            load(source, supplier);
        }
    }

    public Optional<T> getInstance() {
        return Optional.ofNullable(instance);
    }

    public T load() {
        Preconditions.checkNotNull(source, "Source File is null");
        Preconditions.checkNotNull(supplier, "Supplier<T> is null");
        return load(source, supplier);
    }

    public T load(File file, Supplier<T> supplier) {
        instance = uiMapper.load(file, supplier);
        accept(instance);
        return instance;
    }

    public void save() {
        save(instance, source);
    }

    /**
     * Write this ConfigScreen's instance to the given File
     */
    public void save(File file) {
        save(instance, file);
    }

    /**
     * Apply this ConfigScreen's values to the given instance and write it to File
     */
    public void save(T instance, File file) {
        Preconditions.checkNotNull(source, "Source File is null");
        Preconditions.checkNotNull(instance, "Instance is null");
        apply(instance);
        uiMapper.write(instance, file);
    }

    /**
     * Apply the given instance's values to this ConfigScreen's elements
     */
    public void accept(T instance) {
        uiMapper.accept(instance);
    }

    /**
     * Update the given instance with the values held by this ConfigScreen
     */
    public void apply(T instance) {
        uiMapper.apply(instance);
    }

    @Override
    public void keyTyped(char c, int code) throws IOException {
        super.keyTyped(c, code);
        int modifiers = Keys.modifiers(GuiScreen.isCtrlKeyDown(), GuiScreen.isShiftKeyDown(), GuiScreen.isAltKeyDown());
        uiMapper.keyPress(code, c, modifiers);
    }

    @Override
    public void mouseClicked(int mx, int my, int button) throws IOException {
        super.mouseClicked(mx, my, button);
        int modifiers = Keys.modifiers(GuiScreen.isCtrlKeyDown(), GuiScreen.isShiftKeyDown(), GuiScreen.isAltKeyDown());
        uiMapper.mousePress(mx, my, button, modifiers);
    }

    @Override
    public void mouseReleased(int mx, int my, int button) {
        super.mouseReleased(mx, my, button);
        uiMapper.mouseRelease();
    }

    @Override
    public void drawScreen(int mx, int my, float ticks) {
        super.drawScreen(mx, my, ticks);
        uiMapper.setPos(0, 0);
        uiMapper.setSize(width, height);
        uiMapper.draw(this, mx, my);
    }

    @Override
    public void fill(int left, int top, int width, int height, int color) {
        Gui.drawRect(left, top, left + width, top + height, color);
    }

    @Override
    public void drawString(String text, int left, int top, int color) {
        fontRenderer.drawString(text, left, top, color);
    }

    @Override
    public int stringHeight() {
        return fontRenderer.FONT_HEIGHT;
    }

    @Override
    public int stringWidth(String s) {
        onGuiClosed();
        return fontRenderer.getStringWidth(s);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        if (source != null && supplier != null) {
            save();
        }
    }

    public static <T> ConfigScreen<T> of(Class<T> type) {
        return new ConfigScreen<>(type);
    }

    public static <T> ConfigScreen<T> load(File file, Class<T> type, Supplier<T> supplier) {
        return new ConfigScreen<>(file, type, supplier);
    }
}
