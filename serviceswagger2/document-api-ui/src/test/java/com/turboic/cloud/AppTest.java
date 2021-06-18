package com.turboic.cloud;

import static org.junit.Assert.assertTrue;

import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;
import io.github.yedaxia.apidocs.plugin.markdown.MarkdownDocPlugin;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
        DocsConfig docsConfig = new DocsConfig();
        // 项目根目录
        docsConfig.setProjectPath("D:\\cloud\\springcloud-pkq\\serviceswagger2\\document-api-ui");
        // 项目名称
        docsConfig.setProjectName("document-api-ui");
        // 声明该API的版本
        docsConfig.setApiVersion("V1.0");
        // 生成API 文档所在目录
        docsConfig.setDocsPath("D:\\cloud\\springcloud-pkq\\serviceswagger2");
        // 配置自动生成
        docsConfig.setAutoGenerate(Boolean.TRUE);
        //导出markdown
        docsConfig.addPlugin(new MarkdownDocPlugin());
        // 执行生成文档
        Docs.buildHtmlDocs(docsConfig);
    }
}
