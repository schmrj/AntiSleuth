package antisleuthcryptography.asc_api.cryptography.ciphers.asymmetric;

import com.antisleuthsecurity.asc_api.cryptography.ciphers.asymmetric.RsaCipher;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.asymmetric.RsaCipher.CipherInstance;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.builders.RsaCipherBuilder;
import com.antisleuthsecurity.asc_api.exceptions.AscException;

public class RsaTestUtils {
	public static RsaCipher buildEncryptCipher() throws AscException{
		return RsaCipherBuilder
				.getInstance()
				.setInstance(CipherInstance.RSANONEOAEPWithSHA256AndMGF1Padding)
				.setStrength(1024)
				.setEncryptMode()
				.build();
	}
	
	public static RsaCipher buildDecryptCipher() throws AscException{
		return RsaCipherBuilder
				.getInstance()
				.setInstance(CipherInstance.RSANONEOAEPWithSHA256AndMGF1Padding)
				.setStrength(1024)
				.setDecryptMode()
				.build();
	}
}
