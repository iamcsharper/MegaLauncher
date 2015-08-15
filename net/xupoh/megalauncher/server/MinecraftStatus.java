package net.xupoh.megalauncher.server;

import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import net.xupoh.megalauncher.utils.ByteUtils;

public class MinecraftStatus {
	final static byte HANDSHAKE = 9;
	final static byte STAT = 0;
	
	String serverAddress = "localhost";
	int queryPort = 25565; // the default minecraft query port
	int localPort = 25566; // the local port we're connected to the server on
	
	private DatagramSocket socket = null; //prevent socket already bound exception
	private int token;
	
	public MinecraftStatus(String address)
	{
		this(address, 25565);
	}
	public MinecraftStatus(String address, int port)
	{
		serverAddress = address;
		queryPort = port;
	}
	
	// used to get a session token
	private void handshake() throws Exception
	{
		QueryRequest req = new QueryRequest();
		req.type = HANDSHAKE;
		req.sessionID = generateSessionID();
		
		int val = 11 - req.toBytes().length; //should be 11 bytes total
		byte[] input = ByteUtils.padArrayEnd(req.toBytes(), val);
		byte[] result = sendUDP(input);
		
		token = Integer.parseInt(new String(result).trim());
	}

	/**
	 * Use this to get basic status information from the server.
	 * @return a <code>QueryResponse</code> object
	 * @throws Exception 
	 */
	public QueryResponse basicStat() throws Exception
	{
		handshake(); //get the session token first

		QueryRequest req = new QueryRequest(); //create a request
		req.type = STAT;
		req.sessionID = generateSessionID();
		req.setPayload(token);
		byte[] send = req.toBytes();
		
		byte[] result = sendUDP(send);
		
		QueryResponse res = new QueryResponse(result, false);
		return res;
	}
	
	/**
	 * Use this to get more information, including players, from the server.
	 * @return a <code>QueryResponse</code> object
	 * @throws Exception 
	 */
	public QueryResponse fullStat() throws Exception
	{
//		basicStat() calls handshake()
//		QueryResponse basicResp = this.basicStat();
//		int numPlayers = basicResp.onlinePlayers; //TODO use to determine max length of full stat
		
		handshake();
		
		QueryRequest req = new QueryRequest();
		req.type = STAT;
		req.sessionID = generateSessionID();
		req.setPayload(token);
		req.payload = ByteUtils.padArrayEnd(req.payload, 4); //for full stat, pad the payload with 4 null bytes
		
		byte[] send = req.toBytes();
		
		byte[] result = sendUDP(send);
		
		/*
		 * note: buffer size = base + #players(online) * 16(max username length)
		 */
		
		QueryResponse res = new QueryResponse(result, true);
		return res;
	}
	
	private byte[] sendUDP(byte[] input) throws Exception
	{
		try
		{
			while(socket == null)
			{
				try {
					socket = new DatagramSocket(localPort); //create the socket
				} catch (BindException e) {
					++localPort; // increment if port is already in use
				}
			}
			
			//create a packet from the input data and send it on the socket
			InetAddress address = InetAddress.getByName(serverAddress); //create InetAddress object from the address
			DatagramPacket packet1 = new DatagramPacket(input, input.length, address, queryPort);
			socket.send(packet1);
			
			//receive a response in a new packet
			byte[] out = new byte[1024*3]; //TODO guess at max size
			DatagramPacket packet = new DatagramPacket(out, out.length);
			socket.setSoTimeout(1000); //one half second timeout
			socket.receive(packet);
			
			return packet.getData();
		}
		catch (SocketException e)
		{
			e.printStackTrace();
		}
		catch (SocketTimeoutException e)
		{
			//System.exit(1);
			throw new Exception();
		}
		catch (UnknownHostException e)
		{
			System.err.println("Unknown host!");
			e.printStackTrace();
			//System.exit(1);
			// throw exception
		}
		catch (Exception e) //any other exceptions that may occur
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	private int generateSessionID()
	{
		/*
		 * Can be anything, so we'll just use 1 for now. Apparently it can be omitted altogether.
		 * TODO: increment each time, or use a random int
		 */
		return 1;
	}
	
	@Override
	public void finalize()
	{
		socket.close();
	}
	
}
/*
public class MinecraftStatus {
	private Socket socket, Info;
	public $Online, $MOTD, $CurPlayers, $MaxPlayers, $IP, $Port, $Error;
	
	public MinecraftStatus(String ip) {
		
		$this->IP = $IP;
		$this->Port = $Port;			
		
		// Удаляем все протоколы из адреса сервера.
		if(preg_match('/(.*):\/\//', $this->IP)) {
			$this->IP = preg_replace('/(.*):\/\//', '', $this->IP);
		}
		if(strpos($this->IP, '/') !== false) {
			$this->IP = rtrim($this->IP, '/');
			if(strpos($this->IP, '/') !== false) {
				$this->Failed();
				$this->Error = 'Unsupported IP/Domain format, no \'/\'s allowed';
				return;
			}
		}
		if(preg_match_all('/:/', $this->IP, $matches) > 1) {
			unset($matches);
			// IP6
			if(strpos($this->IP, '[') === false && strpos($this->IP, ']') === false)
				$this->IP = '['.$this->IP.']';
		} else if(strpos($this->IP, ':') !== false) {
			$this->Failed();
			$this->Error = 'Unsupported IP/Domain format';
			return;
		}
		
		if($this->Socket = @stream_socket_client('tcp://'.$this->IP.':'.$Port, $ErrNo, $ErrStr, 1)) {
			// Если айпи стандарта IP6, удаляем скобки.
			if(strpos($this->IP, '[') === 0 && strpos($this->IP, ']') === (strlen($this->IP) - 1))
				$this->IP = trim($this->IP, '[]');
			
			$this->Online = true;
			
			fwrite($this->Socket, "\xfe");
			$Handle = fread($this->Socket, 2048);
			$Handle = str_replace("\x00", '', $Handle);
			$Handle = substr($Handle, 2);
			$this->Info = explode("\xa7", $Handle); // Отделяем информацию.
			unset($Handle);
			fclose($this->Socket);
			
			if(sizeof($this->Info) == 3) {
				$this->MOTD       = $this->Info[0];
				$this->CurPlayers = (int)$this->Info[1];
				$this->MaxPlayers = (int)$this->Info[2];
				$this->Error      = false;
			} else if(sizeof($this->Info) > 3) { 
				$Temp = '';
				for($i = 0; $i < sizeof($this->Info) - 2; $i++) {
					$Temp .= ($i > 0 ? '§' : '').$this->Info[$i];
				}
				$this->MOTD       = $Temp;
				$this->CurPlayers = (int)$this->Info[sizeof($this->Info) - 2];
				$this->MaxPlayers = (int)$this->Info[sizeof($this->Info) - 1];
				$this->Error      = 'Faulty motd or outdated script';
			} else {
				$this->Failed();
				$this->Error      = 'Unexpected error, cause may be an outdated script';
			}
		} else {
			$this->Online = false;
			$this->Failed();
			$this->Error = 'Can not reach the server';
		}
	}
	
	public function Info() {
		return array(
			'MOTD'       => $this->MOTD,
			'CurPlayers' => $this->CurPlayers,
			'MaxPlayers' => $this->MaxPlayers
		);
	}
	
	private function Failed() {
		$this->MOTD       = false;
		$this->CurPlayers = false;
		$this->MaxPlayers = false;
	}
}*/