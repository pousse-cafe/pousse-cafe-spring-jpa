package poussecafe.spring.jpa.storage.codegeneration;

import poussecafe.source.analysis.Name;
import poussecafe.source.generation.AggregateAttributesImplementationEditor;
import poussecafe.source.generation.tools.CompilationUnitEditor;
import poussecafe.source.model.Aggregate;

import static java.util.Objects.requireNonNull;

public class JpaAttributesImplementationEditor {

    public void edit() {
        var versionTypeName = new Name("javax.persistence.Version");
        compilationUnitEditor.addImport(versionTypeName);
        var idTypeName = new Name("javax.persistence.Id");
        compilationUnitEditor.addImport(idTypeName);
        var entityTypeName = new Name("javax.persistence.Entity");
        compilationUnitEditor.addImport(entityTypeName);

        var typeEditor = compilationUnitEditor.typeDeclaration();
        typeEditor.modifiers().markerAnnotation(entityTypeName);

        var identifierField = typeEditor.field(AggregateAttributesImplementationEditor.IDENTIFIER_FIELD_NAME).get(0);
        identifierField.modifiers().markerAnnotation(idTypeName);

        var versionField = typeEditor.field(AggregateAttributesImplementationEditor.VERSION_FIELD_NAME).get(0);
        versionField.modifiers().markerAnnotation(versionTypeName);
        versionField.modifiers().removeAnnotations(SuppressWarnings.class);

        compilationUnitEditor.flush();
    }

    private Aggregate aggregate;

    public static class Builder {

        private JpaAttributesImplementationEditor editor = new JpaAttributesImplementationEditor();

        public JpaAttributesImplementationEditor build() {
            requireNonNull(editor.compilationUnitEditor);
            requireNonNull(editor.aggregate);
            return editor;
        }

        public Builder compilationUnitEditor(CompilationUnitEditor compilationUnitEditor) {
            editor.compilationUnitEditor = compilationUnitEditor;
            return this;
        }

        public Builder aggregate(Aggregate aggregate) {
            editor.aggregate = aggregate;
            return this;
        }
    }

    private JpaAttributesImplementationEditor() {

    }

    private CompilationUnitEditor compilationUnitEditor;
}
