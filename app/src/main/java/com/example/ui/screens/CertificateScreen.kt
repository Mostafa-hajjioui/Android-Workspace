package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.viewmodel.AtlasAcademyViewModel

@Composable
fun CertificateScreen(
    viewModel: AtlasAcademyViewModel,
    modifier: Modifier = Modifier
) {
    val profile by viewModel.profile.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CarbonBg)
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Upper Title
        Text(
            text = "🏆 GRADUATION VAULT",
            style = MaterialTheme.typography.labelLarge,
            color = CyberMint,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = "Official Certification",
            style = MaterialTheme.typography.displaySmall,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (!profile.hasGraduated) {
            // LOCKED STATE (With Bypass Button)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .border(1.dp, GridBorder, RoundedCornerShape(4.dp)),
                colors = CardDefaults.cardColors(containerColor = SurfaceSlate)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Locked",
                        tint = MarrakeshSunset,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Certificate Locked",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "To unlock your official summer certificate, you must complete all the core curriculum tasks assigned to your proficiency level in the Syllabus tab.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Divider(color = GridBorder, thickness = 1.dp, modifier = Modifier.padding(bottom = 16.dp))

                    Text(
                        text = "REVIWER PREVIEW:",
                        style = MaterialTheme.typography.labelMedium,
                        color = MarrakeshSunset,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Button(
                        onClick = { viewModel.fastTrackGraduation() },
                        colors = ButtonDefaults.buttonColors(containerColor = CyberMint, contentColor = Color(0xFF030712)),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("FAST-TRACK GRADUATION (UNLOCK 📜)", fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            // GRADUATED STATE - Beautiful Certificate Canvas Design
            Text(
                text = "🎉 CONGRATULATIONS! YOU HAVE GRADUATED 🎉",
                style = MaterialTheme.typography.titleMedium,
                color = CyberMint,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // High Fidelity Code-Designed Digital Certificate
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.72f) // Premium vertical/portrait certificate ratio
                    .background(SurfaceSlate, RoundedCornerShape(4.dp))
                    .border(BorderStroke(2.dp, CyberMint), RoundedCornerShape(4.dp))
                    .padding(12.dp)
            ) {
                // Background Decorative Grid Drawing
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val borderPadding = 12.dp.toPx()
                    val dashedEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    
                    // Outer dashed guideline border
                    drawRect(
                        color = GridBorder,
                        topLeft = Offset(borderPadding, borderPadding),
                        size = size.copy(
                            width = size.width - (borderPadding * 2),
                            height = size.height - (borderPadding * 2)
                        ),
                        style = Stroke(width = 1.dp.toPx(), pathEffect = dashedEffect)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Certificate Header
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "🇲🇦 ATLAS SUMMIT",
                                style = MaterialTheme.typography.labelLarge,
                                color = MarrakeshSunset,
                                fontWeight = FontWeight.Black,
                                fontSize = 12.sp,
                                letterSpacing = 2.sp
                            )
                        }
                        Text(
                            text = "INTENSIVE ENGLISH ACADEMY",
                            style = MaterialTheme.typography.labelMedium,
                            color = TextSecondary,
                            fontSize = 9.sp,
                            letterSpacing = 1.sp
                        )
                    }

                    // Certificate Seal / Ribbon Badge
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Stars,
                            contentDescription = "Success Star Badge",
                            tint = Color(0xFFD4AF37), // Pure gold color
                            modifier = Modifier.size(56.dp)
                        )
                    }

                    // Certificate Body Content
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "CERTIFICATE OF SUCCESS",
                            style = MaterialTheme.typography.titleLarge,
                            color = TextPrimary,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 18.sp,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "This is officially awarded to",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = profile.name.ifEmpty { "Moroccan Explorer" },
                            style = MaterialTheme.typography.displaySmall,
                            color = CyberMint,
                            fontWeight = FontWeight.Black,
                            textAlign = TextAlign.Center,
                            fontSize = 22.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "for successfully executing the 1-month intensive English language & tech collaboration program, demonstrating verified CEFR capabilities in:",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary,
                            textAlign = TextAlign.Center,
                            fontSize = 10.sp,
                            lineHeight = 14.sp,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "LEVEL ACCOMPLISHED: ${profile.assignedLevel.label} (${profile.assignedLevel.code})",
                            style = MaterialTheme.typography.labelLarge,
                            color = AtlasBlue,
                            fontWeight = FontWeight.Black,
                            fontSize = 11.sp
                        )
                    }

                    // Metadata Signatures & Verifier
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Divider(color = GridBorder, thickness = 1.dp, modifier = Modifier.width(120.dp))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Director of Studies, Atlas Academy",
                            style = MaterialTheme.typography.labelMedium,
                            color = TextMuted,
                            fontSize = 9.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "DATE: ${profile.completionDate.ifEmpty { "June 24, 2026" }}",
                                style = MaterialTheme.typography.labelMedium,
                                color = TextMuted,
                                fontSize = 9.sp
                            )
                            Text(
                                text = "ID: ${profile.certificateId}",
                                style = MaterialTheme.typography.labelMedium,
                                color = CyberMint,
                                fontSize = 9.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Dynamic Generator Workbench Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .border(1.dp, CyberMint, RoundedCornerShape(4.dp)),
                colors = CardDefaults.cardColors(containerColor = SurfaceSlateAlt)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "⚙️ GENERATOR REAL-TIME CUSTOMIZATION",
                        style = MaterialTheme.typography.labelLarge,
                        color = CyberMint,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    
                    var certName by remember(profile.name) { mutableStateOf(profile.name) }
                    var certDate by remember(profile.completionDate) { 
                        mutableStateOf(profile.completionDate.ifEmpty { "June 24, 2026" }) 
                    }

                    OutlinedTextField(
                        value = certName,
                        onValueChange = { certName = it },
                        label = { Text("Student Full Name", color = TextSecondary) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CyberMint,
                            unfocusedBorderColor = GridBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                    )

                    OutlinedTextField(
                        value = certDate,
                        onValueChange = { certDate = it },
                        label = { Text("Completion Date", color = TextSecondary) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CyberMint,
                            unfocusedBorderColor = GridBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                viewModel.customizeCertificate(certName, certDate)
                                Toast.makeText(context, "Certificate details updated dynamically!", Toast.LENGTH_SHORT).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = CyberMint, contentColor = Color(0xFF030712)),
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(Icons.Default.Build, contentDescription = "Build", modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("RE-GENERATE", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        OutlinedButton(
                            onClick = {
                                val sdf = java.text.SimpleDateFormat("MMMM dd, yyyy", java.util.Locale.US)
                                certDate = sdf.format(java.util.Date())
                            },
                            border = BorderStroke(1.dp, GridBorder),
                            shape = RoundedCornerShape(4.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = TextSecondary),
                            modifier = Modifier.wrapContentWidth()
                        ) {
                            Text("TODAY", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Copy Block: Academic Credibility description
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .border(1.dp, GridBorder, RoundedCornerShape(4.dp)),
                colors = CardDefaults.cardColors(containerColor = SurfaceSlate)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Verified",
                            tint = CyberMint,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "CEFR Standard Verification",
                            style = MaterialTheme.typography.labelLarge,
                            color = CyberMint
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "This certificate provides parents with academic proof that the student has unlocked practical tenses, situational public speaking formats, and conversational confidence. Ideal for CV building, LinkedIn, and school admissions portfolios.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action CTAs
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        Toast.makeText(context, "Certificate shared to parents' WhatsApp! Verification Code: ${profile.certificateId}", Toast.LENGTH_LONG).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CyberMint, contentColor = Color(0xFF030712)),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.Default.Share, contentDescription = "Share", modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("SHARE ON WHATSAPP", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }

                OutlinedButton(
                    onClick = {
                        viewModel.resetAcademy()
                        Toast.makeText(context, "Academy reset! Take the test with different variables.", Toast.LENGTH_SHORT).show()
                    },
                    border = BorderStroke(1.dp, GridBorder),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextSecondary),
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset", modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("RESET ACADEMY", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}
