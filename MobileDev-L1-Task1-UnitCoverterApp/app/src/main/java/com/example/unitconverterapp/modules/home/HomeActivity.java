package com.example.unitconverterapp.modules.home;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.unitconverterapp.R;
import com.google.android.material.button.MaterialButton;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private static final String INVALID_INPUT = "Invalid input";

    private Spinner categoriesSpinner;
    private Spinner spinnerFromUnit;
    private Spinner spinnerToUnit;

    private EditText etFromValue;
    private TextView tvToValue;
    private TextView tvSwapUnits;

    private MaterialButton btnCopyResult;

    private final Map<String, String[]> categories = new LinkedHashMap<>();

    private final DecimalFormat decimalFormat =
            new DecimalFormat("0.##########",
                    DecimalFormatSymbols.getInstance(Locale.US));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        setupWindowInsets();
        initViews();
        setupCategories();
        setupCategorySpinner();
        setupListeners();

        convertValue();
    }

    // Initialize Views
    private void initViews() {

        categoriesSpinner = findViewById(R.id.categories_spinner);
        spinnerFromUnit = findViewById(R.id.spinnerFromUnit);
        spinnerToUnit = findViewById(R.id.spinnerToUnit);

        etFromValue = findViewById(R.id.etFromValue);
        tvToValue = findViewById(R.id.tvToValue);

        tvSwapUnits = findViewById(R.id.tvSwapUnits);
        btnCopyResult = findViewById(R.id.btnCopyResult);
    }

    // Apply system bars + keyboard insets only.
    // The 16dp content padding lives in the XML (inner LinearLayout).
    private void setupWindowInsets() {

        View root = findViewById(R.id.main);

        ViewCompat.setOnApplyWindowInsetsListener(root, (view, insets) -> {

            Insets bars = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
                            | WindowInsetsCompat.Type.ime()
            );

            view.setPadding(bars.left, bars.top, bars.right, bars.bottom);

            return insets;
        });
    }

    // Define Categories and Units
    private void setupCategories() {

        categories.put("Length", new String[]{
                "km", "m", "cm", "mm", "mi", "ft", "in"
        });

        categories.put("Weight", new String[]{
                "kg", "g", "mg", "lb", "oz"
        });

        categories.put("Temperature", new String[]{
                "°C", "°F", "K"
        });

        categories.put("Time", new String[]{
                "sec", "min", "hr", "day"
        });

        categories.put("Area", new String[]{
                "m²", "km²", "cm²", "ft²", "acre"
        });

        categories.put("Volume", new String[]{
                "L", "mL", "m³", "gal", "fl oz"
        });
    }

    // Setup Category Spinner
    private void setupCategorySpinner() {

        String[] categoryNames =
                categories.keySet().toArray(new String[0]);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                categoryNames
        );

        categoryAdapter.setDropDownViewResource(
                R.layout.spinner_dropdown_item
        );

        categoriesSpinner.setAdapter(categoryAdapter);

        categoriesSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        updateUnitSpinners(categoryNames[position]);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
    }

    // Update Units Based on Selected Category
    private void updateUnitSpinners(String category) {

        String[] units = categories.get(category);

        if (units == null) {
            return;
        }

        ArrayAdapter<String> fromUnitAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                units
        );

        ArrayAdapter<String> toUnitAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                units
        );

        fromUnitAdapter.setDropDownViewResource(
                R.layout.spinner_dropdown_item
        );

        toUnitAdapter.setDropDownViewResource(
                R.layout.spinner_dropdown_item
        );

        spinnerFromUnit.setAdapter(fromUnitAdapter);
        spinnerToUnit.setAdapter(toUnitAdapter);

        // Default units
        spinnerFromUnit.setSelection(0);
        spinnerToUnit.setSelection(units.length > 1 ? 1 : 0);

        convertValue();
    }

    // Setup Listeners
    private void setupListeners() {

        // Listen to input changes
        etFromValue.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {

                convertValue();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Listen to unit selection changes
        AdapterView.OnItemSelectedListener unitListener =
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        convertValue();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                };

        spinnerFromUnit.setOnItemSelectedListener(unitListener);
        spinnerToUnit.setOnItemSelectedListener(unitListener);

        // Swap Units
        tvSwapUnits.setOnClickListener(v -> swapUnits());

        // Copy Result
        btnCopyResult.setOnClickListener(v -> copyResult());
    }

    // Main Conversion Method
    private void convertValue() {

        if (spinnerFromUnit.getSelectedItem() == null ||
                spinnerToUnit.getSelectedItem() == null) {
            return;
        }

        // Some keyboards/locales use "," as the decimal separator
        String input = etFromValue.getText().toString()
                .trim()
                .replace(',', '.');

        if (input.isEmpty() || input.equals(".") ||
                input.equals("-") || input.equals("-.")) {

            tvToValue.setText("0");
            return;
        }

        try {

            double value = Double.parseDouble(input);

            if (!Double.isFinite(value)) {
                tvToValue.setText(INVALID_INPUT);
                return;
            }

            String category = categoriesSpinner.getSelectedItem().toString();

            String fromUnit = spinnerFromUnit.getSelectedItem().toString();

            String toUnit = spinnerToUnit.getSelectedItem().toString();

            double result = performConversion(
                    value, fromUnit, toUnit, category
            );

            if (!Double.isFinite(result)) {
                tvToValue.setText(INVALID_INPUT);
                return;
            }

            // Avoid showing "-0"
            if (result == 0) {
                result = 0;
            }

            tvToValue.setText(decimalFormat.format(result));

        } catch (NumberFormatException e) {

            tvToValue.setText(INVALID_INPUT);
        }
    }

    // Conversion Logic
    private double performConversion(
            double value,
            String fromUnit,
            String toUnit,
            String category) {

        if (fromUnit.equals(toUnit)) {
            return value;
        }

        if (category.equals("Temperature")) {

            double celsius;

            // Convert source unit to Celsius
            switch (fromUnit) {

                case "°C":
                    celsius = value;
                    break;

                case "°F":
                    celsius = (value - 32) * 5 / 9;
                    break;

                case "K":
                    celsius = value - 273.15;
                    break;

                default:
                    return 0;
            }

            // Convert Celsius to destination unit
            switch (toUnit) {

                case "°C":
                    return celsius;

                case "°F":
                    return (celsius * 9 / 5) + 32;

                case "K":
                    return celsius + 273.15;

                default:
                    return 0;
            }
        }

        double fromFactor = getUnitFactor(category, fromUnit);
        double toFactor = getUnitFactor(category, toUnit);

        // Convert to base unit, then to target unit
        double baseValue = value * fromFactor;

        return baseValue / toFactor;
    }

    // Unit Factors Relative to Base Units
    private double getUnitFactor(String category, String unit) {

        switch (category) {

            case "Length":
                switch (unit) {
                    case "km": return 1000;
                    case "m": return 1;
                    case "cm": return 0.01;
                    case "mm": return 0.001;
                    case "mi": return 1609.344;
                    case "ft": return 0.3048;
                    case "in": return 0.0254;
                }
                break;

            case "Weight":
                switch (unit) {
                    case "kg": return 1;
                    case "g": return 0.001;
                    case "mg": return 0.000001;
                    case "lb": return 0.45359237;
                    case "oz": return 0.028349523125;
                }
                break;

            case "Time":
                switch (unit) {
                    case "sec": return 1;
                    case "min": return 60;
                    case "hr": return 3600;
                    case "day": return 86400;
                }
                break;

            case "Area":
                switch (unit) {
                    case "m²": return 1;
                    case "km²": return 1000000;
                    case "cm²": return 0.0001;
                    case "ft²": return 0.09290304;
                    case "acre": return 4046.8564224;
                }
                break;

            case "Volume":
                switch (unit) {
                    case "L": return 1;
                    case "mL": return 0.001;
                    case "m³": return 1000;
                    case "gal": return 3.785411784;
                    case "fl oz": return 0.0295735295625;
                }
                break;
        }

        return 1;
    }

    // Swap Source and Destination Units
    private void swapUnits() {

        int fromPosition = spinnerFromUnit.getSelectedItemPosition();
        int toPosition = spinnerToUnit.getSelectedItemPosition();

        spinnerFromUnit.setSelection(toPosition);
        spinnerToUnit.setSelection(fromPosition);

        convertValue();
    }

    // Copy Conversion Result
    private void copyResult() {

        String result = tvToValue.getText().toString();

        if (result.isEmpty() || result.equals(INVALID_INPUT)) {

            Toast.makeText(
                    this,
                    "No valid result to copy",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        ClipboardManager clipboard =
                (ClipboardManager) getSystemService(
                        Context.CLIPBOARD_SERVICE
                );

        if (clipboard == null) {
            return;
        }

        ClipData clip = ClipData.newPlainText(
                "Conversion Result",
                result
        );

        clipboard.setPrimaryClip(clip);

        // Android 13+ shows its own clipboard confirmation
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            Toast.makeText(
                    this,
                    "Result copied successfully",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}