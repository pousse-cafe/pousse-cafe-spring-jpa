package poussecafe.spring.jpa.storage.codegeneration;

import java.nio.file.Path;
import poussecafe.source.analysis.Name;
import poussecafe.source.generation.AggregateCodeGenerationConventions;
import poussecafe.source.generation.StorageAdaptersCodeGenerator;
import poussecafe.source.generation.tools.ComilationUnitEditor;
import poussecafe.source.model.Aggregate;

import static java.util.Objects.requireNonNull;

public class JpaStorageAdaptersCodeGenerator extends StorageAdaptersCodeGenerator {

    @Override
    public void generate(Aggregate aggregate) {
        super.generate(aggregate);
        addJpaDataRepository(aggregate);
    }

    @Override
    protected void updateDefaultAttributesImplementation(Aggregate aggregate, ComilationUnitEditor compilationUnitEditor) {
        var editor = new JpaAttributesImplementationEditor.Builder()
                .compilationUnitEditor(compilationUnitEditor)
                .aggregate(aggregate)
                .build();
        editor.edit();
    }

    @Override
    protected void addDataAccessImplementation(Aggregate aggregate, ComilationUnitEditor compilationUnitEditor) {
        var editor = new JpaDataAccessImplementationEditor.Builder()
                .compilationUnitEditor(compilationUnitEditor)
                .aggregate(aggregate)
                .build();
        editor.edit();
    }

    private void addJpaDataRepository(Aggregate aggregate) {
        var typeName = aggregateJpaRepositoryTypeName(aggregate);
        var compilationUnitEditor = compilationUnitEditor(typeName);
        var editor = new JpaDataRepositoryEditor.Builder()
                .compilationUnitEditor(compilationUnitEditor)
                .aggregate(aggregate)
                .build();
        editor.edit();
    }

    public static Name aggregateJpaRepositoryTypeName(Aggregate aggregate) {
        return new Name(AggregateCodeGenerationConventions.adaptersPackageName(aggregate),
                aggregate.name() + "DataJpaRepository");
    }

    @Override
    protected String storageName() {
        return STORAGE_NAME;
    }

    public static final String STORAGE_NAME = "Jpa";

    public static class Builder {

        private JpaStorageAdaptersCodeGenerator generator = new JpaStorageAdaptersCodeGenerator();

        public JpaStorageAdaptersCodeGenerator build() {
            requireNonNull(generator.sourceDirectory);
            return generator;
        }

        public Builder sourceDirectory(Path sourceDirectory) {
            generator.sourceDirectory = sourceDirectory;
            return this;
        }
    }

    private JpaStorageAdaptersCodeGenerator() {

    }
}
