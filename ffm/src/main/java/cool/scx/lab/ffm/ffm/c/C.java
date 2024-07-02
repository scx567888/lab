package cool.scx.lab.ffm.ffm.c;

import cool.scx.lab.ffm.ffm.FFMProxy;

public interface C {

    C C = FFMProxy.ffmProxy(null, C.class);

    long strlen(String str);

}
