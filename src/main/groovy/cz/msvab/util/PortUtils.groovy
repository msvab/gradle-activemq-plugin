package cz.msvab.util

class PortUtils {

    @SuppressWarnings("GroovyUnusedCatchParameter")
    static boolean isPortInUse(int port) {
        ServerSocket ss = null;
        DatagramSocket ds = null;
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            ds = new DatagramSocket(port);
            ds.setReuseAddress(true);
            return true;
        } catch (IOException e) {
        } finally {
            if (ds != null) {
                ds.close();
            }

            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {}
            }
        }

        return false;
    }
}
