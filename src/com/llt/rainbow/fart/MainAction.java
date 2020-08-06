package com.llt.rainbow.fart;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.TypedAction;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * @author llt11
 */
public class MainAction extends AnAction {

    static {
        EditorActionManager instance = EditorActionManager.getInstance();
        TypedAction typedAction = instance.getTypedAction();
        TypedActionHandler handler = typedAction.getHandler();
        System.out.println("new init");
        typedAction.setupHandler(new CustomTypedActionHandler(handler));
    }



    @Override
    public void actionPerformed(AnActionEvent e) {
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        EditorActionManager instance = EditorActionManager.getInstance();
        EditorActionHandler actionHandler = instance.getActionHandler(IdeActions.ACTION_EDITOR_CLONE_CARET_BELOW);
        actionHandler.execute(editor, editor.getCaretModel().getCurrentCaret(), e.getDataContext());
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        Project project = e.getData(CommonDataKeys.PROJECT);
        e.getPresentation().setVisible(project != null && editor != null && !editor.getCaretModel().getAllCarets().isEmpty());
    }
}
