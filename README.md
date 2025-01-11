<h1 align="center">📒 eDiary Application</h1>
<p>
    The <strong>eDiary Application</strong> is a feature-rich digital diary system developed using <strong>HTML</strong>, <strong>CSS</strong>, <strong>JavaScript</strong>, <strong>Spring Boot</strong>, <strong>Spring MVC</strong>, <strong>Spring Data JPA</strong>, and <strong>MySQL Database</strong>. It allows users to securely manage their personal diary entries while also providing advanced user account management functionalities.
</p>

<hr>

<h2>🚀 Features</h2>

<h3>👤 User Management</h3>
<ul>
    <li><strong>User Registration:</strong> Store user details securely in the database upon successful registration.</li>
    <li><strong>Login:</strong> Log in to the application with valid credentials.</li>
    <li><strong>Forgot Password:</strong> Reset password using registered <strong>email</strong> and <strong>mobile number</strong>.</li>
    <li><strong>Profile Management:</strong>
        <ul>
            <li>View profile details.</li>
            <li>Update user information.</li>
            <li>Permanently delete user account.</li>
        </ul>
    </li>
</ul>

<h3>📔 Diary Management</h3>
<ul>
    <li><strong>Add Entry:</strong> Create a new diary entry.</li>
    <li><strong>Update Entry:</strong> Edit an existing diary entry.</li>
    <li><strong>View Entries:</strong> View all or specific diary entries.</li>
    <li><strong>Remove Entry:</strong> Delete individual diary entries.</li>
</ul>

<hr>

<h2>🛠️ Technologies Used</h2>
<ul>
    <li><strong>Frontend:</strong> HTML, CSS, JavaScript</li>
    <li><strong>Backend:</strong> Spring Boot, Spring MVC, Spring Data JPA</li>
    <li><strong>Database:</strong> MySQL</li>
</ul>

<hr>

<h2>📁 Project Structure</h2>
<pre>
src/
├── model/             
│   Contains POJO classes annotated with JPA for mapping database tables.
├── repository/        
│   Includes interfaces for database operations using Spring Data JPA.
├── service/           
│   Handles business logic and acts as a bridge between controller and repository layers.
├── controller/        
│   Manages HTTP requests, invokes services, and returns appropriate responses.
└── resources/         
    ├── static/       Contains static files like CSS, JavaScript, and images.
    ├── templates/    Holds JSP files for rendering views.
    └── application.properties  Configuration file for database and application settings.
</pre>

<hr>

<h2>⚙️ How It Works</h2>
<ol>
    <li><strong>User Registration:</strong> Users register by providing their details, which are securely stored in the database.</li>
    <li><strong>Login:</strong> After registration, users log in using their credentials to access the application.</li>
    <li><strong>Forgot Password:</strong> If users forget their password, they can reset it by verifying their email and mobile number.</li>
    <li><strong>Profile Management:</strong> Logged-in users can view, update, or delete their profiles permanently.</li>
    <li><strong>Diary Management:</strong>
        <ul>
            <li><strong>Add Entry:</strong> Users can create new diary entries.</li>
            <li><strong>Update Entry:</strong> Modify existing entries as needed.</li>
            <li><strong>View Entries:</strong> View all or specific diary entries in a structured format.</li>
            <li><strong>Remove Entry:</strong> Delete individual entries when no longer needed.</li>
        </ul>
    </li>
</ol>

<hr>

<h2>🎯 Getting Started</h2>
<ol>
    <li><strong>Clone the Repository</strong>
        <pre>
        git clone https://github.com/your-username/ediary-application.git
        cd ediary-application
        </pre>
    </li>
    <li><strong>Set Up the Database</strong>
        <ul>
            <li>Create a MySQL database (e.g., <code>ediary_db</code>).</li>
            <li>Update database configurations in <code>application.properties</code> file.</li>
        </ul>
    </li>
    <li><strong>Run the Application</strong>
        <pre>
        mvn spring-boot:run
        </pre>
    </li>
    <li><strong>Access the Application</strong>
        <p>Open your browser and go to <code>http://localhost:8080</code>.</p>
    </li>
</ol>

<hr>

<h2>🤝 Contributing</h2>
<p>
    Contributions are welcome!<br>
    Feel free to open issues or submit pull requests to enhance the application.
</p>
