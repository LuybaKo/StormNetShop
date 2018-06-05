import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import spark.Request;
import spark.Response;
import spark.Route;


import static spark.Spark.get;
import static spark.Spark.post;


public class Controller {
    public static void main(String[] args) {
        get("/getAllGoods", getAllGoodRoute());
        get("/findGoodByName", getFindGoodByNameRoute());

    }

    //        Route getRouter = new Route() {
//            @Override
//            public Object handle(Request request, Response response) throws Exception {
//                return "This is Get request";
//            }
//        };
//        Route postRouter = new Route() {
//            @Override
//            public Object handle(Request request, Response response) throws Exception {
//                return "This is Post request";
//            }
//        };
//        get("/simpleGet", getRouter);
//        post("/simplePost", postRouter);
//    }
    private static final ObjectMapper mapper = new ObjectMapper();

    private static Route getAllGoodRoute() {
        Route getRoute = new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String allGoods = mapper.writeValueAsString(ShopDAO.findAllGoods());
                return allGoods;
            }
        };
        return getRoute;
    }
    private static Route getFindGoodByNameRoute() {
        Route getRoute = new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String goodName = request.queryParams("goodName");//ищет значение гет нэйм = банана
                if (StringUtils.isBlank(goodName)) {//библиотека
                    return "Please specify correct good name";
                }

                Good good = ShopDAO.findAllGoods(goodName);
                if (good == null) {
                    return "Good with name " + goodName + " not found";
                }

                String json = mapper.writeValueAsString(good);
                return json;
            }
        };
        return getRoute;
    }
}

