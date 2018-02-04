
package modelo;

public class Item {
    private int idItem;
    private String nome;
    private String status;
    private String comentarioAdmin;
    private Classificacao classificacao= new Classificacao();
    private TipoItem tipoitem = new TipoItem();
    private Usuario usuario = new Usuario();
    private Item itemPai;    

    public Item getItemPai() {
        return itemPai;
    }

    public void setItemPai(Item itemPai) {
        this.itemPai = itemPai;
    }

    public TipoItem getTipoitem() {
        return tipoitem;
    }

    public void setTipoitem(TipoItem tipoitem) {
        this.tipoitem = tipoitem;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComentarioAdmin() {
        return comentarioAdmin;
    }

    public void setComentarioAdmin(String comentarioAdmin) {
        this.comentarioAdmin = comentarioAdmin;
    }
    
        public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }
     public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public TipoItem getTipoItem() {
        return tipoitem;
    }

    public void setTipoItem(TipoItem tipoitem) {
        this.tipoitem = tipoitem;
    }
    public Classificacao getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(Classificacao classificacao) {
        this.classificacao = classificacao;
    }
    
    
    }
    
    
    

