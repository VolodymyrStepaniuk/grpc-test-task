<h1>Unillence Test Task</h1>

<h2>Project Description</h2>

<h3>Table of Contents</h3>

  <ul>
        <li><a href="#requirements">Requirements</a></li>
        <li><a href="#getting-started">Getting Started</a></li>
        <li><a href="#service-methods">Service Methods</a></li>
        <li><a href="#technologies-used">Technologies Used</a></li>
  </ul>

<h3 id="requirements">Requirements</h3>

  <p>This project is designed to meet the following requirements:</p>

  <ol>
        <li>
            <p><strong>Fields:</strong></p>
            <ul>
                <li><strong>Id(UUID)</strong></li>
                <li><strong>Title(String)</strong></li>
                <li><strong>Author(String)</strong></li>
                <li><strong>Isbn(String)</strong></li>
                <li><strong>Quantity(String)</strong></li>
            </ul>
        </li>
        <li>
            <p><strong>Functionality:</strong></p>
            <ul>
                <li><strong>Create Book:</strong> Allows you to create the entity of the book and get a book response </li>
                <li><strong>Get Book by id:</strong> Allows you to get a book by id </li>
                <li><strong>Update Book Fields:</strong> Update one or more fields of a book.</li>
                <li><strong>Delete User:</strong> Remove a book from the system.</li>
                <li><strong>Get all books:</strong> Allows you to get all books from the system</li>
            </ul>
        </li>
        <li>
            <p><strong>Testing:</strong></p>
            <ul>
                <li>Code should be thoroughly covered by unit and integration tests using Testcontainers, PostgreSQL, Citrus Framework.</li>
            </ul>
        </li>
        <li>
            <p><strong>Error Handling:</strong></p>
            <ul>
                <li>Implement error handling for gRPC API endpoints to provide meaningful error messages to
                    clients.</li>
            </ul>
        </li>
  </ol>

<h3 id="getting-started">Getting Started</h3>

  <p>These instructions will guide you through setting up and running the project on your local development environment.</p>

<h4>Prerequisites</h4>

  <p>Before you begin, ensure you have met the following requirements:</p>

  <ul>
        <li>Java 17: Make sure you have Java 17 installed on your machine. You can download it from the <a
                href="https://www.oracle.com/java/technologies/javase-downloads.html">official Oracle website</a> or use a
            distribution like OpenJDK.</li>
        <li>Docker Desktop: Make sure you have Docker Desktop installed on your machine. You can download it from the <a
                href="https://www.docker.com/get-started/">official Docker website
</a></li>
  </ul>

<h4>Clone the Repository</h4>

  <ol>
      <li>Clone this repository to your local machine using your preferred method (HTTPS or SSH):</li>
  </ol>

  <pre><code>git clone https://github.com/your-username/your-project.git</code></pre>

  <p>Replace <code>your-username</code> and <code>your-project</code> with the appropriate GitHub username and project
        repository name.</p>

<h4>Build and Run the Project</h4>

  <ol start="1">
        <li>Navigate to the project directory:</li>
  </ol>

  <pre><code>cd your-project</code></pre>

  <ol start="2">
        <li>Run Docker Compose file using next command:</li>
  </ol>
  <pre><code>docker-compose up</code></pre>
  <ol start="3"><li>Go to <a href="http://localhost:5050">localhost:5050</a> and enter admin@gmail.com to email field and admin to password field (You can change it in <a href="docker-compose.yml">docker-compose.yml</a> file)</li></ol>
  <ol start="4"><li>Create database with name "bookstore" or change url of connecting to DB in <a href="./bookstore-server/src/main/resources/application.yml">application.yml</a></li></ol>

  <ol start="5">
        <li>Build the project using Gradle (or the build tool of your choice):</li>
  </ol>

  <pre><code>./gradlew build</code></pre>

  <p>This command will download project dependencies and compile the code.</p>

  <ol start="6">
        <li>Start the Spring Boot application:</li>
  </ol>

  <pre><code>./gradlew bootRun</code></pre>

  <p>This command will start the application, and it should now be accessible at <a href="http://localhost:8080"
            target="_blank">http://localhost:8080</a> in your web browser.</p>

<h4>Using the API</h4>

  <p>You can now use the gRPC API described in the <a href="#service-methods">Service Methods</a> section to
        interact with the application.</p>

<h4>Running Unit and Integration tests</h4>

  <p>To run the unit and integration tests for the project, you can need to do next steps:</p>
        <li>Run Docker Desktop on your local machine</li>
        <li>Navigate to the project directory:</li>
        <pre><code>cd your-project</code></pre>
        <li>Run the following command:</li>
        <pre><code>./gradlew test</code></pre>
<p>To run the citrus tests for the project, you can need to do next steps:</p>
        <li>Run Docker Desktop on your local machine</li>
        <li>Navigate to the project directory of api:</li>
          <pre><code>cd your-project</code></pre>
        <li>Run docker-compose.yml to start api</li>
          <pre><code>docker-compose up</code></pre>
        <li>Navigate to the project directory of citrus tests:</li>
          <pre><code>cd your-project</code></pre>
        <li>Run the following command:</li>
          <pre><code>./gradlew test</code></pre>

<h3 id="service-methods">Service Methods</h3>

  <p>List and describe the available gRPC API service methods of the application. Include details such as request bodies and expected responses.</p>


<h4 id="book-response"><b>BookResponse</b></h4>
    <pre><code>id: "Book Id"</code>
<code>title: "Book Title"</code>
<code>author: "Book Author"</code>
<code>isbn: "Book ISBN"</code>
<code>quantity: "Book Quantity"</code>
<code>description: "Book Description"</code>
<code>price: "Book Price"</code></pre>

  <h4><b>Create Book</b></h4>
<ul>
    <li>CreateBookRequest:</li>
    <pre><code>title: "Book Title"</code>
<code>author: "Book Author"</code>
<code>isbn: "Book ISBN"</code>
<code>quantity: "Book Quantity"</code>
<code>description: "Book Description"</code>
<code>price: "Book Price"</code></pre>
</ul>

<ul><li>Returns: <a href="#book-response">BookResponse</a></li></ul>

  <h4><b>Get Book by id</b></h4>
<ul>
<li>GetBookRequest:</li>
<pre><code>id: "Book Id"</code></pre>
<ul><li>Returns: <a href="#book-response">BookResponse</a></li></ul>
</ul>

<h4><b>Update Book</b></h4>
<ul>
<li>UpdateBookRequest:</li>
<pre><code>id: "Book Id"</code>
<code>title: "Book Title"</code>
<code>author: "Book Author"</code>
<code>isbn: "Book ISBN"</code>
<code>quantity: "Book Quantity"</code>
<code>description: "Book Description"</code>
<code>price: "Book Price"</code></pre>
</ul>
<ul><li>Returns: <a href="#book-response">BookResponse</a></li></ul>

<h4><b>Delete Book</b></h4>
<ul>
<li>DeleteBookRequest:</li>
<pre><code>id: "Book Id"</code></pre>
</ul>
<ul><li>Returns: <b>DeleteBookResponse(boolean isDeleted)</b></li></ul>

<h4><b>Get All Books</b></h4>
<ul>
<li>Empty</li>
</ul>

<ul><li>Returns: <b>list of <a href="#book-response">BookResponse</a></b></li></ul>

<h3 id="technologies-used">Technologies Used</h3>

  <ul>
        <li>Java 17</li>
        <li>Spring 3.2.4</li>
        <li>PostgreSQL</li>
        <li>JUnit</li>
        <li>Lombok</li>
        <li>Mapstruct</li>
        <li>Gradle</li>
        <li>gRPC</li>
        <li>Protobuf</li>
        <li>Citrus Framework</li>
  </ul>
