import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content view to your XML layout file
        setContentView(R.layout.activity_login)

        // Find the views from the layout file
        val emailEditText = findViewById<EditText>(R.id.email_edit_text)
        val passwordEditText = findViewById<EditText>(R.id.password_edit_text)
        val loginButton = findViewById<Button>(R.id.login_button)

        // Set a click listener for the login button
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Perform basic validation
            if (email.isEmpty() || password.isEmpty()) {
                // Show a toast message if fields are empty
                Toast.makeText(this, "Please enter both email and password.", Toast.LENGTH_SHORT).show()
            } else {
                // Here, you would typically add your login logic.
                // For now, we'll just show a success message.
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

                // Example of navigating to the next activity:
                // val intent = Intent(this, MainActivity::class.java)
                // startActivity(intent)
                // finish() // Call this to close the login activity
            }
        }
    }
}