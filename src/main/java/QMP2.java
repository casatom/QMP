import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

class Institucion{
  String nombre;
  Uniforme uniforme;

  public Institucion(String nombre, Uniforme uniforme) {
    this.nombre = nombre;
    if(uniforme==null){
      throw new InstitucionInvalidaException("falta el unifome");
    }
    this.uniforme = uniforme;
  }

  public Uniforme getUniforme() {
    return uniforme;
  }
}

class Uniforme{

  //tenemos todos los tipos de prendas que hay en el sistema (hay que ver si esta cantidad de tipos es variable)
  TipoPrenda ZAPATO = new TipoPrenda(Categoria.CALZADO);
  TipoPrenda REMERA = new TipoPrenda(Categoria.PARTE_SUPERIOR);
  TipoPrenda PANTALON = new TipoPrenda(Categoria.PARTE_INFERIOR);
  TipoPrenda POLLERA = new TipoPrenda(Categoria.PARTE_INFERIOR);
  TipoPrenda SHORT = new TipoPrenda(Categoria.PARTE_INFERIOR);
  TipoPrenda ZAPATILLAS = new TipoPrenda(Categoria.CALZADO);

  Prenda prendaSuperior;
  Prenda prendaInferior;
  Prenda prendaCalzado;

  //Rellena un uniforme con un calzado, parte inferior y superior con todos sus atributos aleatoreos
  public void sugerencia(Uniforme uniforme){
    uniforme.setPrendaCalzado(cualquierPrenda(cualquierTipoDePrenda(Categoria.CALZADO)));
    uniforme.setPrendaInferior(cualquierPrenda(cualquierTipoDePrenda(Categoria.PARTE_INFERIOR)));
    uniforme.setPrendaSuperior(cualquierPrenda(cualquierTipoDePrenda(Categoria.PARTE_SUPERIOR)));
  }

  public Uniforme(Prenda prendaSuperior, Prenda prendaInferior, Prenda prendaCalzado) {
    if(prendaInferior == null) {
      throw new UniformeInvalidoException("Falta la prenda Inferior");
    }
    if(prendaCalzado == null) {
      throw new UniformeInvalidoException("Falta el Calzado");
    }
    if(prendaSuperior == null) {
      throw new UniformeInvalidoException("Falta el falta la prenda superior");
    }
    this.prendaSuperior = prendaSuperior;
    this.prendaInferior = prendaInferior;
    this.prendaCalzado = prendaCalzado;
  }

  private void setPrendaSuperior(Prenda prendaSuperior) {
    this.prendaSuperior = prendaSuperior;
  }

  private void setPrendaInferior(Prenda prendaInferior) {
    this.prendaInferior = prendaInferior;
  }

  private void setPrendaCalzado(Prenda prendaCalzado) {
    this.prendaCalzado = prendaCalzado;
  }

  private TipoPrenda cualquierTipoDePrenda(Categoria categoria){
    List<TipoPrenda> todosLosTipos = new ArrayList<>();
    agregarTodosLosTiposDePrenda(todosLosTipos);

    todosLosTipos.stream().filter(t -> t.categoriaCoincideCon(categoria)).collect(Collectors.toList());


    int random = new Random().nextInt(todosLosTipos.toArray().length);
    return todosLosTipos.get(random);
  }

  //por cada prenda que se agregue habria que agregar a este add
  private void agregarTodosLosTiposDePrenda(List<TipoPrenda> listaVacia){
    Collections.addAll(listaVacia,ZAPATO,REMERA,PANTALON,POLLERA,SHORT,ZAPATILLAS);
  }

  public Prenda cualquierPrenda(TipoPrenda tipoPrenda){
    return new Prenda(tipoPrenda,randomMaterial(),randomTrama(),randomColor(),randomColor());
  }

  private Material randomMaterial() {
    int random = new Random().nextInt(Material.values().length);
    return Material.values()[random];
  }
  private Color randomColor() {
    int random = new Random().nextInt(Color.values().length);
    return Color.values()[random];
  }
  private TramaDeTela randomTrama() {
    int random = new Random().nextInt(TramaDeTela.values().length);
    return TramaDeTela.values()[random];
  }

}

class Prenda {
  TipoPrenda tipo;
  Material material;
  Color colorPrimario;
  Color colorSecundario;
  TramaDeTela tramaDeTela;

  public Categoria getCategoria(){
    return tipo.getCategoria();
  }

  public Prenda(TipoPrenda tipo, Material material,TramaDeTela tramaDeTela, Color colorPrimario, Color colorSecundario) {
    if(tipo == null) {
      throw new PrendaInvalidaException("Falta el tipo de prenda");
    }
    if(material == null) {
      throw new PrendaInvalidaException("Falta el material");
    }
    if(colorPrimario == null) {
      throw new PrendaInvalidaException("Falta el color principal");
    }

    this.tipo = tipo;
    this.material = material;
    this.colorPrimario = colorPrimario;
    this.colorSecundario = colorSecundario;
    this.tramaDeTela = tramaDeTela;
    if(tramaDeTela==null){
      this.tramaDeTela = TramaDeTela.LISA;
    }
  }

}

class TipoPrenda{
  Categoria categoria;

  public TipoPrenda(Categoria categoria) {
    this.categoria = categoria;
  }

  public Categoria getCategoria() {
    return categoria;
  }

  public Boolean categoriaCoincideCon(Categoria categoria){
    return this.categoria==categoria;
  }
}

enum Material{
  JEAN,
  CUERO,
  CUERINA
}

enum Categoria{
  PARTE_SUPERIOR,
  CALZADO,
  PARTE_INFERIOR,
  ACCESORIOS
}
enum Color{
  ROJO,
  VERDE,
  AZUL
}

enum TramaDeTela{
  LISA,
  RAYADA,
  CON_LUNARES,
  A_CUADROS,
  ESTAMPADO
}

class PrendaInvalidaException extends RuntimeException{

  public PrendaInvalidaException(String causa){
    super("La prenda es invalida porque: " + causa);
  }
}

class UniformeInvalidoException extends RuntimeException{

  public UniformeInvalidoException(String causa){
    super("El uniforme es invalido porque: " + causa);
  }
}

class InstitucionInvalidaException extends RuntimeException{

  public InstitucionInvalidaException(String causa){
    super("La Institucion es invalida porque: " + causa);
  }
}