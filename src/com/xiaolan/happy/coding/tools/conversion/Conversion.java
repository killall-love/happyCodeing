package com.xiaolan.happy.coding.tools.conversion;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.xiaolan.happy.coding.tools.utils.FormatString;
import org.jetbrains.annotations.NotNull;

/**
 * @ProjectName: formatSQL
 * @Package: com.xiaolan.happy.coding.tools.conversion
 * @ClassName: Conversion
 * @Author: 烟花小神
 * @Description:
 * @Date: 2020/12/23 23:53
 * @Version: 1.0
 */
public class Conversion extends AnAction {


    public Conversion() {
        super(IconLoader.getIcon("/icon/conversion.svg"));
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        final Document document = editor.getDocument();

        Caret primaryCaret = editor.getCaretModel().getPrimaryCaret();
        int start = primaryCaret.getSelectionStart();
        int end = primaryCaret.getSelectionEnd();

        String selectedText = editor.getSelectionModel().getSelectedText();
        if (selectedText.isEmpty()) {
            return;
        }
        WriteCommandAction.runWriteCommandAction(project, () ->
                document.replaceString(start, end, FormatString.getConversion(selectedText))
        );
        primaryCaret.removeSelection();

    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        e.getPresentation().setEnabledAndVisible(project != null && editor != null
                && editor.getSelectionModel().hasSelection());
    }
}
