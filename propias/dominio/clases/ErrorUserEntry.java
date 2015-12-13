package propias.dominio.clases;

/**
 * 
 * @author Daniel Sanchez Martinez
 * 
 * Indica els possibles tipus de error al introduir un 
 * usuari nou o ja loguejat
 * 
 */
public enum ErrorUserEntry {
	LOGIN_OK,
	LOGIN_FAIL_USER_EMPTY,
	LOGIN_FAIL_USER_EXISTS,
	LOGIN_FAIL_EMPTY_PASSWORDS,
	LOGIN_FAIL_PASSWORDS_DISTINCT,
	LOGIN_FAIL_ONLY_VALID_CHARS_AND_NUMBERS
}