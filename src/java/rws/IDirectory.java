package rws;

import java.util.List;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

public interface IDirectory {

        public static enum Type {
            DIRECTORY,
            FILE;
        }

        /**
         * Метод позволяет получить список подкаталогов и файлов заданного
              * каталога.
         * @param dirName имя каталога, содержание которого должен вернуть 
         * данный метод.
         * @return коллекция типа {@link java.util.Map<K,V> Map}, в которой 
         * первый параметр содержит строку с именем файла или подкаталога. 
         * Второй параметр определяет чем является элемента каталога с данным 
         * именем: файлом или каталогом. Если заданный каталог пуст, то метод 
         * возвращает пустую коллекцию. Если имя каталога задано неверно 
         * (такого каталога нет), то метод возвращает значение null.
         */
        @GET
        @Path("{directory}")
        @Produces("text/html")
        public String getContent(@PathParam("directory") String dirName);
        /**
         * Метод производит поиск файла в заданном каталоге.
         * @param dirName определяет каталог поиска.
         * @param fileName определяет шаблон имени искомого файла.
         * @return список файлов и подкаталогов заданного каталога dirName, 
         * имена которых удовлетворяют заданному шаблону fileName. Первый 
         * параметр коллекции (ключ)содержит строку с именем файла или 
         * подкаталога. Второй параметр определяет чем является элемента 
         * каталога с данным именем: файлом или каталогом. Если заданный 
         * каталог пуст, то метод возвращает пустую коллекцию. Если имя то
         * каталога задано неверно (такого каталога нет), метод возвращает 
              * значение null.
         */
        @GET
        @Path("find/{directory}")
        //@Produces(MediaType.TEXT_HTML)
        @Produces("text/html")
        public String findFile(@PathParam("directory") String dirName, @QueryParam("file") String fileName);
        
        @GET
        @Path("findall/{directory}")
        //@Produces(MediaType.TEXT_HTML)
        @Produces("text/html")
        public String findAll(@PathParam("directory") String dirName);
}
