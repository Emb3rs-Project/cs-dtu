# EMB3Rs Decision-Making Framework

The EMB3Rs Decision-Making Framework is a tool that helps evaluate the EMB3Rs platform's ability to simulate solutions for industrial excess thermal energy recovery and explore its applicability in other use cases, mainly in terms of non-functional requirements. The framework consists of two main sub-tasks:

- Investigating the utilization of excess heat using a Multi-Criteria Decision-Making (MCDM) model
- Employing a decision tree-based model to examine the platform's applicability to other use cases beyond those considered in the EMB3Rs project

The framework differs from the replicability analysis in T5.1, which focuses on assessing the technical and economic feasibility of replicating the EMB3Rs solutions in different industrial sectors and regions. The framework aims to evaluate the suitability of the EMB3Rs platform for various use cases that involve excess heat recovery and utilization, considering multiple criteria such as technical, economic, environmental, and social aspects.

## MCDM-based Analysis for Excess Heat Utilization

The MCDM-based analysis is a sub-task that uses the Analytic Hierarchy Process (AHP) to assist in making complex decisions by providing a structured framework for weighing multiple factors and considering their relative importance. The AHP calculator can help compare various excess heat utilization options, such as selling it to nearby industries or converting it into electricity, and choose the most viable option based on the decision-maker's preferences and priorities. Applying MCDM in the excess heat industry can help pinpoint the most sustainable and efficient solution, ultimately contributing to the transition to a circular and low-carbon economy.

## Decision Tree-based Analysis for Platform Applicability

The decision tree-based analysis is a sub-task that uses a machine learning algorithm to classify different use cases based on their characteristics and determine whether they are suitable for applying the EMB3Rs platform or not. The decision tree model can help identify the key features that influence the platform's applicability and provide a clear and intuitive way of visualizing the decision process. The decision tree model can also help discover new potential use cases that can benefit from the EMB3Rs platform.

## Getting Started

To use the EMB3Rs Decision-Making Framework, make sure all the libraries are installed mentioned in requirements.txt. Then run the following command in the directory to run webpage:

```
streamlit run MCDM.py
```

If the streamlit environment variable is not set then run below command:

```
~/.local/bin/streamlit run MCDM.py
```

## Tools and Language Used

- Python version 3.11.2
- VSCode Code Editor
- Streamlit python library for developing frontend
- Scikit-learn python library for machine learning

## Live Demo

For live demo click [here](https://vis.flexsus.org/).

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.
"""