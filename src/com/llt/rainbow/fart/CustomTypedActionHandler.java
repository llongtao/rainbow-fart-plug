package com.llt.rainbow.fart;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author llt11
 */
public class CustomTypedActionHandler implements TypedActionHandler {

    private TypedActionHandler handler;

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public CustomTypedActionHandler(TypedActionHandler handler) {
        this.handler = handler;
    }

    @Override
    public void execute(@NotNull Editor editor, char c, @NotNull DataContext dataContext) {
        executorService.execute(()->VoiceManager.addChar(c));
        handler.execute(editor,c,dataContext);
    }
}
