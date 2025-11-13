

public class Book{
	String genre;
	String title;
	String author;
	int pages;
	int price;
	
	Book(String genre,String title,String author,int pages,int price){
		this.genre=genre;
		this.title=title;
		this.author=author;
		this.pages=pages;
		this.price=price;
		
		}
		public static void main(String[] args) {
		Book b=new Book("xyz","the art of laziness","asdj",100,500);
		System.out.println(b.genre);
		
		
	}

}
