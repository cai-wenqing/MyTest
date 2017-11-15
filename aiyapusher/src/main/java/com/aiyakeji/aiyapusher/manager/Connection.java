package com.aiyakeji.aiyapusher.manager;


import android.content.Context;

import com.aiyakeji.aiyapusher.MqttAndroidClient;
import com.aiyakeji.aiyapusher.R;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

/**
 * Represents a {@link MqttAndroidClient} and the actions it has performed
 */
public class Connection {

    /**
     * The host that the {@link MqttAndroidClient} represented by this <code>Connection</code> is represented by
     **/
    private String host = null;

    /**
     * The clientId of the client associated with this <code>Connection</code> object
     **/
    private String clientId = null;

    /**
     * The port on the server that this client is connecting to
     **/
    private int port = 0;

    /**
     * {@link ConnectionStatus } of the {@link MqttAndroidClient} represented by this <code>Connection</code> object. Default value is {@link ConnectionStatus#NONE}
     **/
    private ConnectionStatus status = ConnectionStatus.NONE;


    /**
     * The {@link MqttAndroidClient} instance this class represents
     **/
    private MqttAndroidClient client = null;


    /**
     * The {@link Context} of the application this object is part of
     **/
    private Context context = null;

    /**
     * The {@link MqttConnectOptions} that were used to connect this client
     **/
    private MqttConnectOptions mqttConnectOptions;

    /**
     * True if this connection is secured using TLS
     **/
    private boolean tlsConnection = true;


    /**
     * Connections status for  a connection
     */
    public enum ConnectionStatus {

        /**
         * Client is Connecting
         **/
        CONNECTING,
        /**
         * Client is Connected
         **/
        CONNECTED,
        /**
         * Client is Disconnecting
         **/
        DISCONNECTING,
        /**
         * Client is Disconnected
         **/
        DISCONNECTED,
        /**
         * Client has encountered an Error
         **/
        ERROR,
        /**
         * Status is unknown
         **/
        NONE
    }


    /**
     * Creates a connection from persisted information in the database store, attempting
     * to create a {@link MqttAndroidClient} and the client handle.
     *
     * @param clientId      The id of the client
     * @param host          the server which the client is connecting to
     * @param port          the port on the server which the client will attempt to connect to
     * @param context       the application context
     * @param tlsConnection true if the connection is secured by SSL
     * @return a new instance of <code>Connection</code>
     */
    public static Connection createConnection(String clientId, String host, int port, Context context, boolean tlsConnection) {

        String uri;
        if (tlsConnection) {
            uri = "ssl://" + host + ":" + port;
        } else {
            uri = "tcp://" + host + ":" + port;
        }

        MqttAndroidClient client = new MqttAndroidClient(context, uri, clientId);
        return new Connection(clientId, host, port, context, client, tlsConnection);
    }


    public void updateConnection(String clientId, String host, int port, boolean tlsConnection) {
        String uri;
        if (tlsConnection) {
            uri = "ssl://" + host + ":" + port;
        } else {
            uri = "tcp://" + host + ":" + port;
        }

        this.clientId = clientId;
        this.host = host;
        this.port = port;
        this.tlsConnection = tlsConnection;
        this.client = new MqttAndroidClient(context, uri, clientId);
    }


    /**
     * Creates a connection object with the server information and the client
     * hand which is the reference used to pass the client around activities
     *
     * @param clientId      The Id of the client
     * @param host          The server which the client is connecting to
     * @param port          The port on the server which the client will attempt to connect to
     * @param context       The application context
     * @param client        The MqttAndroidClient which communicates with the service for this connection
     * @param tlsConnection true if the connection is secured by SSL
     */
    private Connection(String clientId, String host,
                       int port, Context context, MqttAndroidClient client, boolean tlsConnection) {
        this.clientId = clientId;
        this.host = host;
        this.port = port;
        this.context = context;
        this.client = client;
        this.tlsConnection = tlsConnection;
    }


    /**
     * Determines if the client is connected
     *
     * @return is the client connected
     */
    public boolean isConnected() {
        return status == ConnectionStatus.CONNECTED;
    }


    /**
     * Changes the connection status of the client
     *
     * @param connectionStatus The connection status of this connection
     */
    public void changeConnectionStatus(ConnectionStatus connectionStatus) {
        status = connectionStatus;
    }

    /**
     * A string representing the state of the client this connection
     * object represents
     *
     * @return A string representing the state of the client
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(clientId);
        sb.append("\n ");

        switch (status) {
            case CONNECTED:
                sb.append(context.getString(R.string.connection_connected_to));
                break;
            case DISCONNECTED:
                sb.append(context.getString(R.string.connection_disconnected_from));
                break;
            case NONE:
                sb.append(context.getString(R.string.connection_unknown_status));
                break;
            case CONNECTING:
                sb.append(context.getString(R.string.connection_connecting_to));
                break;
            case DISCONNECTING:
                sb.append(context.getString(R.string.connection_disconnecting_from));
                break;
            case ERROR:
                sb.append(context.getString(R.string.connection_error_connecting_to));
        }
        sb.append(" ");
        sb.append(host);

        return sb.toString();
    }

    /**
     * Get the client Id for the client this object represents
     *
     * @return the client id for the client this object represents
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Get the host name of the server that this connection object is associated with
     *
     * @return the host name of the server this connection object is associated with
     */
    public String getHostName() {
        return host;
    }

    /**
     * Gets the client which communicates with the org.eclipse.paho.android.service service.
     *
     * @return the client which communicates with the org.eclipse.paho.android.service service
     */
    public MqttAndroidClient getClient() {
        return client;
    }
}
