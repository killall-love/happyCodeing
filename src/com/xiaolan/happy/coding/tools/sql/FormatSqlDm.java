package com.xiaolan.happy.coding.tools.sql;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.util.JdbcConstants;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import org.apache.http.util.TextUtils;
import org.jetbrains.annotations.NotNull;

/**
 * @ProjectName: formatSQL
 * @Package: com.xiaolan.dm.sql.tools
 * @ClassName: FormatSqlDm
 * @Author: 烟花小神
 * @Description:
 * @Date: 2020/12/23 22:51
 * @Version: 1.0
 */
public class FormatSqlDm extends AnAction {

    public FormatSqlDm() {
        super(IconLoader.getIcon("/icon/sql.svg"));
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        SelectionModel model = editor.getSelectionModel();
        final String selectedText = model.getSelectedText();
        if (TextUtils.isEmpty(selectedText)) {
            return;
        }
        final Document document = editor.getDocument();
        Caret primaryCaret = editor.getCaretModel().getPrimaryCaret();
        int start = primaryCaret.getSelectionStart();
        int end = primaryCaret.getSelectionEnd();
        WriteCommandAction.runWriteCommandAction(project, () ->
                document.replaceString(start, end, SQLUtils.format(selectedText, JdbcConstants.DM))
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

