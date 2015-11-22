all:
	# Comanda per generar tot
	javac ./common/*.java ./propias/dominio/clases/*.java ./propias/dominio/controladores/*.java ./propias/dominio/controladores/generator/*.java ./propias/dominio/controladores/generator/dlx/*.java ./propias/persistencia/*.java ./propias/presentacion/*.java main.java ./propias/Driver/*.java

clean:
	rm ./common/*.class ./propias/dominio/clases/*.class ./propias/dominio/controladores/*.class ./propias/dominio/controladores/generator/*.class ./propias/dominio/controladores/generator/dlx/*.class ./propias/persistencia/*.class ./propias/presentacion/*.class main.class ./propias/Driver/*.class
