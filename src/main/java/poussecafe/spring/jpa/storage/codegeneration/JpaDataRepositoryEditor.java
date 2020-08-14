package poussecafe.spring.jpa.storage.codegeneration;

import poussecafe.source.analysis.Name;
import poussecafe.source.generation.AggregateCodeGenerationConventions;
import poussecafe.source.generation.tools.AstWrapper;
import poussecafe.source.generation.tools.ComilationUnitEditor;
import poussecafe.source.generation.tools.Visibility;
import poussecafe.source.model.Aggregate;

import static java.util.Objects.requireNonNull;

@SuppressWarnings("unchecked")
public class JpaDataRepositoryEditor {

    public void edit() {
        compilationUnitEditor.setPackage(AggregateCodeGenerationConventions.adaptersPackageName(aggregate));

        var jpaRepositoryTypeName = new Name("org.springframework.data.jpa.repository.JpaRepository");
        compilationUnitEditor.addImportLast(jpaRepositoryTypeName);

        var typeEditor = compilationUnitEditor.typeDeclaration();

        typeEditor.modifiers().setVisibility(Visibility.PUBLIC);
        typeEditor.setInterface(true);
        var typeName = JpaStorageAdaptersCodeGenerator.aggregateJpaRepositoryTypeName(aggregate);
        typeEditor.setName(typeName);

        var mongoRepositoryType = ast.newParameterizedType(jpaRepositoryTypeName.getIdentifier());
        mongoRepositoryType.typeArguments().add(ast.newSimpleType(
                AggregateCodeGenerationConventions.aggregateAttributesImplementationTypeName(aggregate).getIdentifier()));
        mongoRepositoryType.typeArguments().add(ast.newSimpleType("String"));
        typeEditor.addSuperinterface(mongoRepositoryType);

        compilationUnitEditor.flush();
    }

    private Aggregate aggregate;

    public static class Builder {

        private JpaDataRepositoryEditor editor = new JpaDataRepositoryEditor();

        public JpaDataRepositoryEditor build() {
            requireNonNull(editor.compilationUnitEditor);
            requireNonNull(editor.aggregate);

            editor.ast = new AstWrapper(editor.compilationUnitEditor.ast());

            return editor;
        }

        public Builder compilationUnitEditor(ComilationUnitEditor compilationUnitEditor) {
            editor.compilationUnitEditor = compilationUnitEditor;
            return this;
        }

        public Builder aggregate(Aggregate aggregate) {
            editor.aggregate = aggregate;
            return this;
        }
    }

    private JpaDataRepositoryEditor() {

    }

    private ComilationUnitEditor compilationUnitEditor;

    private AstWrapper ast;
}
