package pl.empik.recruitment.adapters.out.sql.entries;

public interface EntryDict {


    String GEN = "GEN";

    interface BASE {
        String ID = "ID";
    }


    interface LOGIN_REGISTER extends BASE {
        String _TABLE = "LOGIN_REGISTER";
        String _SEQ = GEN + "_" + _TABLE + "_" + BASE.ID;
        String LOGIN = "LOGIN";
        String REQUEST_COUNT = "REQUEST_COUNT";
    }

}
