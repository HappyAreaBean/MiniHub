package net.fantasyrealms.minihub.config;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.experimental.Accessors;
import revxrsal.spec.ArrayCommentStyle;
import revxrsal.spec.CommentedConfiguration;
import revxrsal.spec.SpecReference;
import revxrsal.spec.Specs;

import java.io.File;
import java.util.Collections;
import java.util.List;

@Accessors(fluent = true)
public class SpecConfig<T> extends CommentedConfiguration {

    private final List<String> head;
    @Getter
    private final SpecReference<T> reference;
    private final T spec;
    @Getter
    private final File file;

    public SpecConfig(Class<T> type, List<String> headers, File file, ArrayCommentStyle arrayCommentStyle, Gson gson) {
        super(file.toPath(), gson == null ? CommentedConfiguration.GSON : gson, arrayCommentStyle);
        this.head = headers;
        this.reference = Specs.reference(type, this);
        this.spec = reference.get();
        this.file = file;
    }

    public SpecConfig(Class<T> type, List<String> headers, File file, Gson gson) {
        this(type, headers, file, ArrayCommentStyle.COMMENT_FIRST_ELEMENT, gson);
    }

    public SpecConfig(Class<T> type, File file, Gson gson) {
        this(type, Collections.emptyList(), file, ArrayCommentStyle.COMMENT_FIRST_ELEMENT, gson);
    }

    public SpecConfig(Class<T> type, List<String> headers, File file) {
        this(type, headers, file, ArrayCommentStyle.COMMENT_FIRST_ELEMENT, null);
    }

    public SpecConfig(Class<T> type, File file) {
        this(type, Collections.emptyList(), file, ArrayCommentStyle.COMMENT_FIRST_ELEMENT, null);
    }

    @Override
    public void save() {
        setHeaders(head);
        super.save();
    }

    @Override
    public void load() {
        setHeaders(head);
        super.load();
    }

    public void saveAndReload() {
        reference.save();
        reference.reload();
    }

    public void loadAndSave() {
        reference.reload();
        reference.save();
    }

    public T config() {
        return this.spec;
    }

    public void reload() {
        reference.reload();
    }

    public static <T> SpecConfig<T> create(Class<T> type, File file) {
        return new SpecConfig<>(type, file);
    }

    public static <T> SpecConfig<T> create(Class<T> type, File file, Gson gson) {
        return new SpecConfig<>(type, file, gson);
    }

    public static <T> SpecConfig<T> create(Class<T> type, List<String> headers, File file) {
        return new SpecConfig<>(type, headers, file);
    }

    public static <T> T spec(Class<T> type, List<String> headers, File file) {
        return create(type, headers, file).spec;
    }

    public static <T> SpecConfig<T> create(Class<T> type, List<String> headers, File file, Gson gson) {
        return new SpecConfig<>(type, headers, file, gson);
    }

    public static <T> T spec(Class<T> type, List<String> headers, File file, Gson gson) {
        return create(type, headers, file, gson).spec;
    }

    public static <T> SpecConfig<T> create(Class<T> type, List<String> headers, File file, ArrayCommentStyle arrayCommentStyle) {
        return new SpecConfig<>(type, headers, file, arrayCommentStyle, null);
    }

    public static <T> T spec(Class<T> type, List<String> headers, File file, ArrayCommentStyle arrayCommentStyle) {
        return create(type, headers, file, arrayCommentStyle).spec;
    }

    public static <T> SpecConfig<T> create(Class<T> type, List<String> headers, File file, ArrayCommentStyle arrayCommentStyle, Gson gson) {
        return new SpecConfig<>(type, headers, file, arrayCommentStyle, gson);
    }

    public static <T> T spec(Class<T> type, List<String> headers, File file, ArrayCommentStyle arrayCommentStyle, Gson gson) {
        return create(type, headers, file, arrayCommentStyle, gson).spec;
    }

}
